package com.lambdanum.raids.application;

import com.lambdanum.raids.infrastructure.injection.McLogger;
import com.lambdanum.raids.infrastructure.utils.minecraft.DebugMcLogger;
import com.lambdanum.raids.raid.RaidAlreadyActiveOnDimensionException;
import com.lambdanum.raids.raid.controller.RaidCommandSender;
import com.lambdanum.raids.raid.controller.RaidController;
import com.lambdanum.raids.raid.controller.RaidControllerRepository;
import com.lambdanum.raids.raid.controller.RaidPlayDimensionRepository;
import com.lambdanum.raids.raid.controller.objective.ObjectivePoller;
import com.lambdanum.raids.raid.controller.objective.RaidObjectiveAbstractFactory;
import com.lambdanum.raids.raid.controller.party.Party;
import com.lambdanum.raids.raid.controller.party.RaidPartyRepository;
import com.lambdanum.raids.script.NestedCommandParser;
import com.lambdanum.raids.script.ScriptCommand;
import com.lambdanum.raids.script.ServerCommandObjectiveSubscriber;

import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import org.apache.commons.lang3.StringUtils;

public class RaidService {

    private RaidControllerRepository raidControllerRepository;
    private RaidObjectiveAbstractFactory objectiveAbstractFactory;
    private McLogger logger;
    private NestedCommandParser commandParser;
    private RaidPlayDimensionRepository playDimensionRepository;
    private RaidPartyRepository raidPartyRepository;

    public RaidService(RaidControllerRepository raidControllerRepository, RaidObjectiveAbstractFactory objectiveAbstractFactory, DebugMcLogger logger, NestedCommandParser commandParser, RaidPlayDimensionRepository playDimensionRepository, RaidPartyRepository raidPartyRepository) {
        this.raidControllerRepository = raidControllerRepository;
        this.objectiveAbstractFactory = objectiveAbstractFactory;
        this.logger = logger;
        this.commandParser = commandParser;
        this.playDimensionRepository = playDimensionRepository;
        this.raidPartyRepository = raidPartyRepository;
    }

    public void addObjective(int dimension, List<String> args) {
        ScriptCommand scriptCommand = commandParser.parse(StringUtils.join(args, " "));

        RaidController controller = raidControllerRepository.getController(dimension);
        ServerCommandObjectiveSubscriber callbackOnObjectiveCompletion = new ServerCommandObjectiveSubscriber(scriptCommand.ifStatements, controller);
        ObjectivePoller objective = objectiveAbstractFactory.create(callbackOnObjectiveCompletion, scriptCommand.conditionClause);

        controller.addObjective(objective);

        logger.log(String.format("added objective %s on dimension %s",objective.getObjectiveName() , dimension));
    }

    public boolean isInARaid(ICommandSender sender) {
        int dimension = getSenderDimension(sender);
        return raidControllerRepository.isRaidActiveInDimension(dimension);
    }

    public int getSenderDimension(ICommandSender sender) {
        if (sender instanceof EntityPlayer) {
            return ((EntityPlayer) sender).dimension;
        }
        if (sender instanceof CommandBlockBaseLogic) {
            return sender.getEntityWorld().provider.getDimension();
        }
        if (sender instanceof RaidCommandSender) {
            return ((RaidCommandSender) sender).getDimension();
        }
        return 0;
    }

    public boolean senderCanControlRaid(ICommandSender sender) {
        return isInARaid(sender) && (sender.canUseCommand(3, "") || sender instanceof CommandBlockBaseLogic);
    }

    public void triggerDefeat(int dimension) {
        RaidController controller = raidControllerRepository.getController(dimension);
        controller.triggerDefeat();
    }

    public void triggerVictory(int dimension) {
        RaidController controller = raidControllerRepository.getController(dimension);
        controller.triggerVictory();
    }

    public void startRaid(ICommandSender sender, String raidName) {
        int playDimension = playDimensionRepository.getAvailablePlayDimension();
        if (raidControllerRepository.isRaidActiveInDimension(playDimension)) {
            throw new RaidAlreadyActiveOnDimensionException();
        }
        Party party = raidPartyRepository.getPlayerParty(sender.getName());
        RaidController controller = raidControllerRepository.createController(raidName, playDimension, party);
        controller.startMapInitialization();
    }
}
