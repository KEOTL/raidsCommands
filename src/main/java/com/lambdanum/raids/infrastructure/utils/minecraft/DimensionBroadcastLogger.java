package com.lambdanum.raids.infrastructure.utils.minecraft;

import com.lambdanum.raids.infrastructure.injection.McLogger;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class DimensionBroadcastLogger implements McLogger {

    private MinecraftServer minecraftServer;
    private ICommandSender commandSender;

    public DimensionBroadcastLogger(MinecraftServer minecraftServer, ICommandSender commandSender) {
        this.minecraftServer = minecraftServer;
        this.commandSender = commandSender;
    }

    @Override
    public void log(String message) {
        minecraftServer.commandManager.executeCommand(commandSender, "say " + message);
    }
}
