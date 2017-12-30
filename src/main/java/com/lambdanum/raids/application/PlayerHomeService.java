package com.lambdanum.raids.application;

import com.lambdanum.raids.home.PlayerHomeRepository;
import com.lambdanum.raids.model.Position;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.GameType;

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
            player.setGameType(GameType.SURVIVAL);
        }).start();
    }

    public void asyncSetPlayerHome(String playerName, Position home) {
        new Thread(() -> setPlayerHome(playerName, home)).start();
    }

    public void setPlayerHome(String playerName, Position home) {
        homeRepository.setPlayerHome(playerName, home);
    }
}
