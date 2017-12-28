package com.lambdanum.raids.context;

import com.lambdanum.raids.application.LootService;
import com.lambdanum.raids.application.OnlinePlayerService;
import com.lambdanum.raids.application.PlayerHomeService;
import com.lambdanum.raids.application.PlayerTeleportService;
import com.lambdanum.raids.home.PlayerHomeRepository;
import com.lambdanum.raids.infrastructure.AbstractBinder;
import com.lambdanum.raids.infrastructure.McLogger;
import com.lambdanum.raids.infrastructure.MinecraftBroadcastLogger;
import com.lambdanum.raids.infrastructure.MinecraftServerProvider;
import com.lambdanum.raids.infrastructure.persistence.InMemoryRaidRepository;
import com.lambdanum.raids.infrastructure.persistence.RestLootTableProvider;
import com.lambdanum.raids.infrastructure.persistence.RestPlayerHomeRepository;
import com.lambdanum.raids.loot.LootTableProvider;
import com.lambdanum.raids.raid.controller.RaidControllerProvider;
import com.lambdanum.raids.raid.controller.RaidControllerWatchdog;
import com.lambdanum.raids.raid.controller.RaidRepository;

import net.minecraft.server.MinecraftServer;

public class MainBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(InMemoryRaidRepository.class).to(RaidRepository.class);
        bind(RaidControllerProvider.class).to(RaidControllerProvider.class);
        bind(new MinecraftServerProvider()).to(MinecraftServer.class);
        bind(MinecraftBroadcastLogger.class).to(MinecraftBroadcastLogger.class);
        bind(MinecraftBroadcastLogger.class).to(McLogger.class);
        bind(RaidControllerWatchdog.class).to(RaidControllerWatchdog.class);
        bind(PlayerTeleportService.class).to(PlayerTeleportService.class);
        bind(PlayerHomeService.class).to(PlayerHomeService.class);
        bind(RestPlayerHomeRepository.class).to(PlayerHomeRepository.class);
        bind(RestLootTableProvider.class).to(LootTableProvider.class);
        bind(LootService.class).to(LootService.class);
        bind(OnlinePlayerService.class).to(OnlinePlayerService.class);
        install(UtilBinder.class);
        install(CommandBinder.class);
    }
}
