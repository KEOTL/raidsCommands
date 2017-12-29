package com.lambdanum.raids.infrastructure.utils.minecraft;

import com.lambdanum.raids.infrastructure.injection.RuntimeProvider;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class MinecraftServerProvider implements RuntimeProvider<MinecraftServer> {

    @Override
    public MinecraftServer get() {
        return FMLCommonHandler.instance().getMinecraftServerInstance();
    }
}
