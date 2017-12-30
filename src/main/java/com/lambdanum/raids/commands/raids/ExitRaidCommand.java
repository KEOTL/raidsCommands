package com.lambdanum.raids.commands.raids;

import com.lambdanum.raids.application.RaidExitService;
import com.lambdanum.raids.application.RaidService;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class ExitRaidCommand implements ICommand {

    private RaidService raidService;
    private RaidExitService raidExitService;

    public ExitRaidCommand(RaidService raidService, RaidExitService raidExitService) {
        this.raidService = raidService;
        this.raidExitService = raidExitService;
    }

    @Override
    public String getName() {
        return "exit-raid";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "exit-raid";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player = (EntityPlayer) sender;
        raidExitService.sendPlayerHome(player);
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender instanceof EntityPlayer && raidService.isInARaid(sender);
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
