package com.lambdanum.raids.application;

import com.lambdanum.raids.raid.controller.party.OnlinePlayerNotFoundException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class OnlinePlayerService {

    private MinecraftServer minecraftServer;

    public OnlinePlayerService(MinecraftServer minecraftServer) {
        this.minecraftServer = minecraftServer;
    }

    public EntityPlayer getPlayerByUsername(String playerName) {
        EntityPlayer player = minecraftServer.getPlayerList().getPlayerByUsername(playerName);
        if (player == null) {
            throw new OnlinePlayerNotFoundException();
        }
        return player;
    }

    public boolean isPlayerOnline(String playerName) {
        try {
            getPlayerByUsername(playerName);
            return true;
        } catch (OnlinePlayerNotFoundException e) {
            return false;
        }
    }
}
