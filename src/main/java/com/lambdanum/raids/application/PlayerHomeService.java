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

    public void teleportPlayerToHome(EntityPlayer player) {
        Position playerHome = homeRepository.getPlayerHome(player.getName());
        teleportService.teleportPlayerToDimension(player,0 ,playerHome);
        player.setGameType(GameType.SURVIVAL);
    }

    public void asyncSetPlayerHome(String playerName, Position home) {
        new Thread(() -> setPlayerHome(playerName, home)).start();
    }

    public void setPlayerHome(String playerName, Position home) {
        homeRepository.setPlayerHome(playerName, home);
    }
}
