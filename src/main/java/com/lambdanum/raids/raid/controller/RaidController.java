package com.lambdanum.raids.raid.controller;

import com.lambdanum.raids.application.LootService;
import com.lambdanum.raids.application.PlayerTeleportService;
import com.lambdanum.raids.infrastructure.injection.ComponentLocator;
import com.lambdanum.raids.infrastructure.injection.McLogger;
import com.lambdanum.raids.infrastructure.utils.minecraft.DebugMcLogger;
import com.lambdanum.raids.infrastructure.utils.minecraft.RegionCloner;
import com.lambdanum.raids.model.Raid;
import com.lambdanum.raids.raid.controller.objective.ObjectivePoller;
import com.lambdanum.raids.raid.controller.party.Party;
import com.lambdanum.raids.raid.controller.party.RaidPartyRepository;

import java.util.List;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldServer;

public class RaidController {

    private final McLogger logger = ComponentLocator.INSTANCE.get(DebugMcLogger.class);
    private final RegionCloner regionCloner = ComponentLocator.INSTANCE.get(RegionCloner.class);
    private final PlayerTeleportService teleportService = ComponentLocator.INSTANCE.get(PlayerTeleportService.class);
    private final MinecraftServer minecraftServer = ComponentLocator.INSTANCE.get(MinecraftServer.class);
    private final LootService lootService = ComponentLocator.INSTANCE.get(LootService.class);

    private Raid raid;
    private final int dimension;
    private Party party;
    private RaidCommandSender commandSender;

    private ObjectiveController objectiveController;

    private RaidStatus status = RaidStatus.STARTING_UP;
    private WorldServer playWorld;

    public RaidController(Raid raid, int playDimension, Party party) {
        this.raid = raid;
        this.dimension = playDimension;
        this.party = party;

        playWorld = minecraftServer.getWorld(dimension);

        commandSender = new RaidCommandSender(playWorld);
        objectiveController = new ObjectiveController(commandSender);

        logger.log("Initialized raidController on dimension :" + dimension);
    }

    public boolean isRaidActive() {
        return status == RaidStatus.STARTING_UP || party.areAllMembersInDimension(dimension);
    }

    public void startMapInitialization() {

        WorldServer cleanRaidMap = minecraftServer.getWorld(raid.backupDimension);

        party.setGameType(GameType.SPECTATOR);
        party.teleportAllPlayers(teleportService, dimension, raid.spawn);

        regionCloner.killEntitiesInDestinationExcludingPlayers(commandSender, raid.region);
        regionCloner.cloneRegionToOtherDimension(cleanRaidMap, playWorld, raid.region);
        logger.log("done initializing raid map '" + raid.name + "' on dimension " + dimension);

        party.setGameType(GameType.ADVENTURE);
        party.teleportAllPlayers(teleportService, dimension, raid.spawn);

        logger.log("teleported players to play dimension " + dimension);
        status = RaidStatus.RUNNING;

        executeScript(raid.startupScript);
    }

    private void executeScript(List<String> script) {
        script.forEach(this::executeCommandInRaid);
    }

    public void executeCommandInRaid(String command) {
        logger.log(command);
        minecraftServer.commandManager.executeCommand(commandSender, command);
    }

    public String getRaidName() {
        return raid.name;
    }

    public void addObjective(ObjectivePoller objective) {
        objectiveController.addObjectiveAndSetupPolling(objective);
    }

    public void stop() {
        objectiveController.stopAllPollers();
        logger.log("done stopping objective pollers on dimension " + dimension);
        status = RaidStatus.STOPPED;
    }

    public void triggerDefeat() {
        status = RaidStatus.DEFEAT;
        executeScript(raid.defeatScript);
    }

    public void triggerVictory() {
        status = RaidStatus.VICTORY;
        executeScript(raid.victoryScript);
        lootService.asyncDistributeTeamLoot(party, raid);
    }

    public void resynchronizePartyWithRepository(RaidPartyRepository raidPartyRepository) {
        if (!raidPartyRepository.contains(party)) {
            party = Party.EMPTY_PARTY;
            logger.log("raid controller on dimension " + dimension + " is inconsistent with PartyRepository. Forcing empty party.");
        }
    }
}
