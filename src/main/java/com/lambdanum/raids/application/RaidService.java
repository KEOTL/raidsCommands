package com.lambdanum.raids.application;

import com.lambdanum.raids.infrastructure.injection.McLogger;
import com.lambdanum.raids.raid.controller.RaidController;
import com.lambdanum.raids.raid.controller.RaidControllerProvider;
import com.lambdanum.raids.raid.controller.objective.ObjectivePoller;
import com.lambdanum.raids.raid.controller.objective.RaidObjectiveAbstractFactory;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityCommandBlock;

public class RaidService {

    private RaidControllerProvider raidControllerProvider;
    private RaidObjectiveAbstractFactory objectiveAbstractFactory;
    private McLogger logger;

    public RaidService(RaidControllerProvider raidControllerProvider, RaidObjectiveAbstractFactory objectiveAbstractFactory, McLogger logger) {
        this.raidControllerProvider = raidControllerProvider;
        this.objectiveAbstractFactory = objectiveAbstractFactory;
        this.logger = logger;
    }

    public void addObjective(int dimension, String objectiveType, String... args) {
        RaidController controller = raidControllerProvider.getController(dimension);
        ObjectivePoller objective = objectiveAbstractFactory.create(controller, objectiveType, args);

        // Probably not necessary since they run in their own thread
        controller.addObjective(objective);

        logger.log(String.format("added objective %s on dimension %s", objectiveType, dimension));
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
        return false;
    }
}
