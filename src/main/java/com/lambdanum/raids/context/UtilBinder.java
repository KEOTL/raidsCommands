package com.lambdanum.raids.context;

import com.google.gson.Gson;
import com.lambdanum.raids.HttpHelper;
import com.lambdanum.raids.context.config.ServerProperties;
import com.lambdanum.raids.infrastructure.AbstractBinder;
import com.lambdanum.raids.infrastructure.utils.minecraft.RegionCloner;

public class UtilBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(RegionCloner.class).to(RegionCloner.class);
        bind(Gson.class).to(Gson.class);
        bind(HttpHelper.class).to(HttpHelper.class);
        bind(ServerProperties.class).to(ServerProperties.class);
    }
}
