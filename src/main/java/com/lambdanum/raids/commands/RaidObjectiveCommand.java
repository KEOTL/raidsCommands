package com.lambdanum.raids.commands;

import com.lambdanum.raids.application.RaidService;
import com.lambdanum.raids.raid.controller.RaidCommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.math.BlockPos;

public class RaidObjectiveCommand implements ICommand {

    private RaidService raidService;

    public RaidObjectiveCommand(RaidService raidService) {
        this.raidService = raidService;
    }

    @Override
    public String getName() {
        return "objective";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "objective <type> <params...>";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        ArrayList<String> objectiveArgs = new ArrayList<>();
        objectiveArgs.addAll(Arrays.asList(args));

        int dimension = 0;

        if (sender instanceof EntityPlayer) {
            dimension = ((EntityPlayer) sender).dimension;
        }
        if (sender instanceof TileEntityCommandBlock) {
            dimension = ((TileEntityCommandBlock) sender).getWorld().provider.getDimension();
        }
        if (sender instanceof RaidCommandSender) {
            dimension = ((RaidCommandSender) sender).getDimension();
        }

        raidService.addObjective(dimension, Arrays.asList(args));
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return raidService.isInARaid(sender) && sender.canUseCommand(3, "");
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Collections.emptyList();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand iCommand) {
        return 0;
    }
}
