package com.lambdanum.raids.application;

import com.lambdanum.raids.model.Position;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class RaidExitService {

    private PlayerTeleportService teleportService;
    private PlayerHomeService homeService;
    private MinecraftServer minecraftServer;

    public RaidExitService(PlayerTeleportService teleportService, PlayerHomeService homeService, MinecraftServer minecraftServer) {
        this.teleportService = teleportService;
        this.homeService = homeService;
        this.minecraftServer = minecraftServer;
    }

    public void sendPlayerHome(EntityPlayer player) {
        Position overworldSpawn = new Position(minecraftServer.getWorld(0).getSpawnPoint());
        teleportService.teleportPlayerToDimension(player, 0, overworldSpawn);
        homeService.asyncTeleportPlayerToHome(player);
    }
}
