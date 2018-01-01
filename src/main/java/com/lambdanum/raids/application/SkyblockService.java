package com.lambdanum.raids.application;

import com.lambdanum.raids.minecraft.OnlinePlayerRepository;
import com.lambdanum.raids.model.Position;
import com.lambdanum.raids.skyblock.IslandGenerationStrategy;
import com.lambdanum.raids.skyblock.SkyblockUserRepository;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.GameType;

public class SkyblockService {

    private LootService lootService;
    private PlayerHomeService homeService;
    private IslandGenerationStrategy islandGenerationStrategy;
    private OnlinePlayerRepository onlinePlayerRepository;
    private SkyblockUserRepository skyblockUserRepository;

    public SkyblockService(LootService lootService, PlayerHomeService homeService, IslandGenerationStrategy islandGenerationStrategy, OnlinePlayerRepository onlinePlayerRepository, SkyblockUserRepository skyblockUserRepository) {
        this.lootService = lootService;
        this.homeService = homeService;
        this.islandGenerationStrategy = islandGenerationStrategy;
        this.onlinePlayerRepository = onlinePlayerRepository;
        this.skyblockUserRepository = skyblockUserRepository;
    }

    public void asyncSetupPlayerIsland(String playerName, Position playerPosition) {
        new Thread(() -> {
            islandGenerationStrategy.generateIsland(playerPosition);
            EntityPlayer player = onlinePlayerRepository.getPlayerByUsername(playerName);
            player.setGameType(GameType.SURVIVAL);
            player.setPosition(playerPosition.x, playerPosition.y + 2, playerPosition.z);
            lootService.distributeLoot(playerName, "startup");
            homeService.asyncSetPlayerHome(playerName, new Position(playerPosition.x - 1, playerPosition.y + 1, playerPosition.z));
            homeService.teleportPlayerToHome(player);
            skyblockUserRepository.setUserHasAlreadyGeneratedIsland(playerName);
        }).start();
    }

}
