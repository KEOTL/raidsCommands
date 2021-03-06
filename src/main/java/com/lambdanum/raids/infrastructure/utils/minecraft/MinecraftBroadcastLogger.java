package com.lambdanum.raids.infrastructure.utils.minecraft;

import com.lambdanum.raids.infrastructure.injection.McLogger;

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
