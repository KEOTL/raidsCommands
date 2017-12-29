package com.lambdanum.raids.application;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class OnlinePlayerService {

    private MinecraftServer minecraftServer;

    public OnlinePlayerService(MinecraftServer minecraftServer) {
        this.minecraftServer = minecraftServer;
    }

    public EntityPlayer getPlayerByUsername(String playerName) {
        return minecraftServer.getPlayerList().getPlayerByUsername(playerName);
    }
}
