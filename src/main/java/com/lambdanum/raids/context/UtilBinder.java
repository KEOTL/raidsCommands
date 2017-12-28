package com.lambdanum.raids.context;

import com.google.gson.Gson;
import com.lambdanum.raids.infrastructure.AbstractBinder;
import com.lambdanum.raids.infrastructure.utils.minecraft.RegionCloner;

public class UtilBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(RegionCloner.class).to(RegionCloner.class);
        bind(Gson.class).to(Gson.class);
    }
}
