package com.lambdanum.raids.minecraft;

import com.lambdanum.raids.raid.controller.party.OnlinePlayerNotFoundException;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class OnlinePlayerRepository {

    private MinecraftServer minecraftServer;

    public OnlinePlayerRepository(MinecraftServer minecraftServer) {
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

    public List<EntityPlayer> getPlayersInDimension(int dimension) {
        return minecraftServer.getWorld(dimension).getPlayers(EntityPlayer.class, i -> true);
    }
}
