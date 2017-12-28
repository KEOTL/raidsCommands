package com.lambdanum.raids.application;

import com.lambdanum.raids.model.Position;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class PlayerTeleportService {

    private MinecraftServer minecraftServer;

    public PlayerTeleportService(MinecraftServer minecraftServer) {
        this.minecraftServer = minecraftServer;
    }

    public void teleportPlayer(EntityPlayer player, Position position) {
        player.attemptTeleport(position.x, position.y, position.z);
    }

    public void teleportPlayerToDimension(EntityPlayer player, int dimension, Position position) {
        minecraftServer.commandManager.executeCommand(minecraftServer, String.format("/tpd %d %s %d %d %d",dimension, player.getName(), position.x, position.y, position.z));
    }
}
