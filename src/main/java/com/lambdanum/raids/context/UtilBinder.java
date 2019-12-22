package com.lambdanum.raids.context;

import com.google.gson.Gson;
import com.lambdanum.raids.HttpHelper;
import com.lambdanum.raids.RaidsMod;
import com.lambdanum.raids.RaidsModConfig;
import com.lambdanum.raids.context.config.ServerProperties;
import com.lambdanum.raids.context.config.ServerPropertiesProvider;
import com.lambdanum.raids.infrastructure.injection.AbstractBinder;
import com.lambdanum.raids.infrastructure.injection.McLogger;
import com.lambdanum.raids.infrastructure.utils.minecraft.DebugMcLogger;
import com.lambdanum.raids.infrastructure.utils.minecraft.MinecraftBroadcastLogger;
import com.lambdanum.raids.infrastructure.utils.minecraft.RegionCloner;
import com.lambdanum.raids.infrastructure.utils.minecraft.SystemOutputLogger;
import com.lambdanum.raids.script.NestedCommandParser;

public class UtilBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(RegionCloner.class).to(RegionCloner.class);
        bind(Gson.class).to(Gson.class);
        bind(HttpHelper.class).to(HttpHelper.class);
        bind(new ServerPropertiesProvider()).to(ServerProperties.class);
        bind(NestedCommandParser.class).to(NestedCommandParser.class);

        bind(MinecraftBroadcastLogger.class).to(MinecraftBroadcastLogger.class);
        bind(MinecraftBroadcastLogger.class).to(McLogger.class);
        bind(SystemOutputLogger.class).to(DebugMcLogger.class);
    }
}
