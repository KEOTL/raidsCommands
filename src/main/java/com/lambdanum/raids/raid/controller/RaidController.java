package com.lambdanum.raids.raid.controller;

import com.lambdanum.raids.application.PlayerTeleportService;
import com.lambdanum.raids.infrastructure.injection.ComponentLocator;
import com.lambdanum.raids.infrastructure.utils.minecraft.MinecraftBroadcastLogger;
import com.lambdanum.raids.infrastructure.utils.minecraft.RegionCloner;
import com.lambdanum.raids.model.Raid;
import com.lambdanum.raids.raid.controller.objective.ObjectivePoller;
import com.lambdanum.raids.raid.controller.objective.ObjectiveSubscriber;
import com.lambdanum.raids.raid.controller.party.Party;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldServer;

public class RaidController implements ObjectiveSubscriber {

    private final MinecraftBroadcastLogger logger = ComponentLocator.INSTANCE.get(MinecraftBroadcastLogger.class);
    private final RegionCloner regionCloner = ComponentLocator.INSTANCE.get(RegionCloner.class);
    private final PlayerTeleportService teleportService = ComponentLocator.INSTANCE.get(PlayerTeleportService.class);
    private final MinecraftServer minecraftServer = ComponentLocator.INSTANCE.get(MinecraftServer.class);

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

        System.out.println("Initialized raidController on dimension :" + dimension);
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

        executeStartupScript(minecraftServer, raid.startupScript);
    }

    private void executeStartupScript(MinecraftServer minecraftServer, String[] startupScript) {
        for (String command : startupScript) {
            logger.log(command);
            minecraftServer.commandManager.executeCommand(commandSender, command);
        }
    }

    @Override
    public void notifyOnWatchedCondition() {
        // Something has happened inside this raid.
        logger.log("something has happened");
    }

    public String getRaidName() {
        return raid.name;
    }

    public void addObjective(ObjectivePoller objective) {
        objectiveController.addObjectiveAndSetupPolling(objective);
    }

    public void stop() {
        logger.log("stopping all objective pollers");
        objectiveController.stopAllPollers();
        logger.log("done stopping objective pollers");
    }
}
