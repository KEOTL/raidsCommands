package com.lambdanum.raids.application;

import com.lambdanum.raids.infrastructure.injection.McLogger;
import com.lambdanum.raids.raid.controller.RaidCommandSender;
import com.lambdanum.raids.raid.controller.RaidController;
import com.lambdanum.raids.raid.controller.RaidControllerProvider;
import com.lambdanum.raids.raid.controller.objective.ObjectivePoller;
import com.lambdanum.raids.raid.controller.objective.RaidObjectiveAbstractFactory;
import com.lambdanum.raids.script.NestedCommandParser;
import com.lambdanum.raids.script.ScriptCommand;
import com.lambdanum.raids.script.ServerCommandObjectiveSubscriber;

import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityCommandBlock;
import org.apache.commons.lang3.StringUtils;

public class RaidService {

    private RaidControllerProvider raidControllerProvider;
    private RaidObjectiveAbstractFactory objectiveAbstractFactory;
    private McLogger logger;
    private NestedCommandParser commandParser;

    public RaidService(RaidControllerProvider raidControllerProvider, RaidObjectiveAbstractFactory objectiveAbstractFactory, McLogger logger, NestedCommandParser commandParser) {
        this.raidControllerProvider = raidControllerProvider;
        this.objectiveAbstractFactory = objectiveAbstractFactory;
        this.logger = logger;
        this.commandParser = commandParser;
    }

    public void addObjective(int dimension, List<String> args) {
        ScriptCommand scriptCommand = commandParser.parse(StringUtils.join(args, " "));

        RaidController controller = raidControllerProvider.getController(dimension);
        ServerCommandObjectiveSubscriber callbackOnObjectiveCompletion = new ServerCommandObjectiveSubscriber(scriptCommand.ifStatements, controller);
        ObjectivePoller objective = objectiveAbstractFactory.create(callbackOnObjectiveCompletion, scriptCommand.conditionClause);

        controller.addObjective(objective);

        logger.log(String.format("added objective %s on dimension %s",objective.getObjectiveName() , dimension));
    }

    public boolean isInARaid(ICommandSender sender) {
        logger.log(sender.getClass().getName());
        if (sender instanceof EntityPlayer) {
            int dimension = ((EntityPlayer) sender).dimension;
            return raidControllerProvider.isRaidActiveInDimension(dimension);
        }
        if (sender instanceof TileEntityCommandBlock) {
            int dimension = ((TileEntityCommandBlock) sender).getWorld().provider.getDimension();
            return raidControllerProvider.isRaidActiveInDimension(dimension);
        }
        if (sender instanceof RaidCommandSender) {
            int dimension = ((RaidCommandSender) sender).getDimension();
            return raidControllerProvider.isRaidActiveInDimension(dimension);
        }
        return false;
    }
}
