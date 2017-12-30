package com.lambdanum.raids.context;

import com.lambdanum.raids.application.LootService;
import com.lambdanum.raids.application.OnlinePlayerService;
import com.lambdanum.raids.application.PlayerHomeService;
import com.lambdanum.raids.application.PlayerTeleportService;
import com.lambdanum.raids.application.RaidExitService;
import com.lambdanum.raids.application.RaidService;
import com.lambdanum.raids.application.SkyblockService;
import com.lambdanum.raids.commands.raids.RaidObjectiveCommand;
import com.lambdanum.raids.home.PlayerHomeRepository;
import com.lambdanum.raids.infrastructure.injection.AbstractBinder;
import com.lambdanum.raids.infrastructure.injection.McLogger;
import com.lambdanum.raids.infrastructure.persistence.InMemoryRaidRepository;
import com.lambdanum.raids.infrastructure.persistence.RestPlayerHomeRepository;
import com.lambdanum.raids.infrastructure.persistence.RestSkyblockUserRepository;
import com.lambdanum.raids.infrastructure.utils.minecraft.MinecraftBroadcastLogger;
import com.lambdanum.raids.infrastructure.utils.minecraft.MinecraftServerProvider;
import com.lambdanum.raids.raid.controller.RaidControllerProvider;
import com.lambdanum.raids.raid.controller.RaidControllerWatchdog;
import com.lambdanum.raids.raid.controller.RaidRepository;
import com.lambdanum.raids.raid.controller.objective.RaidObjectiveAbstractFactory;
import com.lambdanum.raids.skyblock.IslandGenerationStrategy;
import com.lambdanum.raids.skyblock.SkyblockUserRepository;

import net.minecraft.server.MinecraftServer;

public class MainBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(InMemoryRaidRepository.class).to(RaidRepository.class);

        bind(new MinecraftServerProvider()).to(MinecraftServer.class);
        bind(MinecraftBroadcastLogger.class).to(MinecraftBroadcastLogger.class);
        bind(MinecraftBroadcastLogger.class).to(McLogger.class);
        bind(RaidControllerWatchdog.class).to(RaidControllerWatchdog.class);
        bind(PlayerTeleportService.class).to(PlayerTeleportService.class);
        bind(PlayerHomeService.class).to(PlayerHomeService.class);
        bind(RestPlayerHomeRepository.class).to(PlayerHomeRepository.class);
        bind(LootService.class).to(LootService.class);
        bind(OnlinePlayerService.class).to(OnlinePlayerService.class);
        bind(SkyblockService.class).to(SkyblockService.class);
        bind(IslandGenerationStrategy.class).to(IslandGenerationStrategy.class);
        bind(RestSkyblockUserRepository.class).to(SkyblockUserRepository.class);
        bind(RaidService.class).to(RaidService.class);
        bind(RaidObjectiveCommand.class).to(RaidObjectiveCommand.class);
        bind(RaidObjectiveAbstractFactory.class).to(RaidObjectiveAbstractFactory.class);
        bind(RaidExitService.class).to(RaidExitService.class);
        install(UtilBinder.class);
        install(CommandBinder.class);
        install(LootingBinder.class);

        bind(new RaidControllerProviderProvider()).to(RaidControllerProvider.class);
    }
}
