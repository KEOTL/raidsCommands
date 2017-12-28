package com.lambdanum.raids.application;

import com.lambdanum.raids.home.PlayerHomeRepository;
import com.lambdanum.raids.model.Position;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerHomeService {

    private PlayerTeleportService teleportService;
    private PlayerHomeRepository homeRepository;

    public PlayerHomeService(PlayerTeleportService teleportService, PlayerHomeRepository homeRepository) {
        this.teleportService = teleportService;
        this.homeRepository = homeRepository;
    }

    public void asyncTeleportPlayerToHome(EntityPlayer player) {
        new Thread(() -> {
            Position playerHome = homeRepository.getPlayerHome(player.getName());
            teleportService.teleportPlayer(player, playerHome);
        }).start();
    }

    public void asyncSetPlayerHome(String playerName, Position home) {
        new Thread(() -> {
            homeRepository.setPlayerHome(playerName, home);
        }).start();
    }
}
