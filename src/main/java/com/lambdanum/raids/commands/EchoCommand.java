package com.lambdanum.raids.commands;

import com.lambdanum.raids.infrastructure.MinecraftBroadcastLogger;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class EchoCommand implements ICommand {

    private MinecraftBroadcastLogger logger;

    public EchoCommand(MinecraftBroadcastLogger logger) {
        this.logger = logger;
    }

    @Override
    public String getName() {
        return "echo";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "echo";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        StringBuilder message = new StringBuilder();
        for (String arg : args) {
            message.append(arg + " ");
        }
        logger.log(message.toString());
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
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
