package com.lambdanum.raids.util;

import net.minecraft.server.MinecraftServer;

public class MinecraftBroadcastLogger {

    private MinecraftServer minecraftServer;

    public MinecraftBroadcastLogger(MinecraftServer minecraftServer) {
        this.minecraftServer = minecraftServer;
    }

    public void log(String message) {
        minecraftServer.commandManager.executeCommand(minecraftServer,"say " + message);
    }

}
