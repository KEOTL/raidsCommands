package com.lambdanum.raids.commands;

import com.lambdanum.raids.model.Position;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class VisitCommand implements ICommand {

    private HomeCommand homeCommand;

    public VisitCommand(HomeCommand homeCommand) {
        this.homeCommand = homeCommand;
    }

    @Override
    public String getName() {
        return "visit";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/visit <player name>";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer) sender;
        if (args.length == 1) {
            Position playerHome = homeCommand.getHomeForPlayer(args[0]);
            player.attemptTeleport(playerHome.x, playerHome.y, playerHome.z);
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        if (sender instanceof EntityPlayer) {
            return ((EntityPlayer)sender).dimension == 0;
        }
        return false;
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
