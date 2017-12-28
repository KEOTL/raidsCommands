package com.lambdanum.raids.util;

import com.lambdanum.raids.controller.McLogger;

import net.minecraft.server.MinecraftServer;

public class MinecraftBroadcastLogger implements McLogger {

    private MinecraftServer minecraftServer;

    public MinecraftBroadcastLogger(MinecraftServer minecraftServer) {
        this.minecraftServer = minecraftServer;
    }

    @Override
    public void log(String message) {
        minecraftServer.commandManager.executeCommand(minecraftServer,"say " + message);
    }

}
