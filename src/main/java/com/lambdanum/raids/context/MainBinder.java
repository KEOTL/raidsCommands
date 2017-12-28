package com.lambdanum.raids.context;

import com.lambdanum.raids.commands.CommandBinder;
import com.lambdanum.raids.controller.InMemoryRaidRepository;
import com.lambdanum.raids.controller.RaidControllerProvider;
import com.lambdanum.raids.controller.RaidControllerWatchdog;
import com.lambdanum.raids.controller.RaidRepository;
import com.lambdanum.raids.controller.RegionCloner;
import com.lambdanum.raids.util.AbstractBinder;
import com.lambdanum.raids.util.McLogger;
import com.lambdanum.raids.util.MinecraftBroadcastLogger;
import com.lambdanum.raids.util.MinecraftServerProvider;

import net.minecraft.server.MinecraftServer;

public class MainBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(InMemoryRaidRepository.class).to(RaidRepository.class);
        bind(RaidControllerProvider.class).to(RaidControllerProvider.class);
        bind(new MinecraftServerProvider()).to(MinecraftServer.class);
        bind(MinecraftBroadcastLogger.class).to(MinecraftBroadcastLogger.class);
        bind(RegionCloner.class).to(RegionCloner.class);
        bind(MinecraftBroadcastLogger.class).to(McLogger.class);
        bind(RaidControllerWatchdog.class).to(RaidControllerWatchdog.class);
        install(CommandBinder.class);
    }
}
