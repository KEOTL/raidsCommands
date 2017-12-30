package com.lambdanum.raids.commands.raids;

import com.lambdanum.raids.application.RaidService;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class VictoryCommand implements ICommand {

    private RaidService raidService;

    public VictoryCommand(RaidService raidService) {
        this.raidService = raidService;
    }

    @Override
    public String getName() {
        return "victory";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "victory";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        int dimension = raidService.getSenderDimension(sender);
        raidService.triggerVictory(dimension);
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return raidService.senderCanControlRaid(sender);
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
