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

public class SetHomeCommand implements ICommand {

    private HomeCommand homeCommand;

    public SetHomeCommand(HomeCommand homeCommand) {
        this.homeCommand = homeCommand;
    }

    @Override
    public String getName() {
        return "sethome";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/sethome";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender instanceof EntityPlayer && args.length == 0) {
            EntityPlayer player = (EntityPlayer) sender;
            homeCommand.setHomeForPlayer(player.getName(), new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()));
        }
        if (sender instanceof EntityPlayer && args.length == 1) {
            EntityPlayer player = (EntityPlayer) sender;
            homeCommand.setHomeForPlayer(args[0], new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()));
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        if (sender.canUseCommand(3,"")) {
            return true;
        }
        if (sender instanceof EntityPlayer) {
            return ((EntityPlayer )sender).dimension == 0;
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
