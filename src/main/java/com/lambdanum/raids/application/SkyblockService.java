package com.lambdanum.raids.application;

import com.lambdanum.raids.model.Position;
import com.lambdanum.raids.skyblock.IslandGenerationStrategy;
import com.lambdanum.raids.skyblock.SkyblockUserRepository;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.GameType;

public class SkyblockService {

    private LootService lootService;
    private PlayerHomeService homeService;
    private IslandGenerationStrategy islandGenerationStrategy;
    private OnlinePlayerService onlinePlayerService;
    private SkyblockUserRepository skyblockUserRepository;

    public SkyblockService(LootService lootService, PlayerHomeService homeService, IslandGenerationStrategy islandGenerationStrategy, OnlinePlayerService onlinePlayerService, SkyblockUserRepository skyblockUserRepository) {
        this.lootService = lootService;
        this.homeService = homeService;
        this.islandGenerationStrategy = islandGenerationStrategy;
        this.onlinePlayerService = onlinePlayerService;
        this.skyblockUserRepository = skyblockUserRepository;
    }

    public void asyncSetupPlayerIsland(String playerName, Position playerPosition) {
        new Thread(() -> {
            islandGenerationStrategy.generateIsland(playerPosition);
            EntityPlayer player = onlinePlayerService.getPlayerByUsername(playerName);
            player.setGameType(GameType.SURVIVAL);
            player.setPosition(playerPosition.x, playerPosition.y + 2, playerPosition.z);
            lootService.loot(playerName, "startup");
            homeService.asyncSetPlayerHome(playerName, new Position(playerPosition.x - 1, playerPosition.y + 1, playerPosition.z));
            homeService.asyncTeleportPlayerToHome(player);
            skyblockUserRepository.setUserHasAlreadyGeneratedIsland(playerName);
        }).start();
    }

}
