package com.lambdanum.raids.util;

import com.lambdanum.raids.controller.InMemoryRaidRepository;
import com.lambdanum.raids.controller.RaidControllerProvider;
import com.lambdanum.raids.controller.RaidRepository;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.gui.MinecraftServerGui;
import net.minecraftforge.fml.common.FMLCommonHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;

public class ComponentLocator {

    public static final ComponentLocator INSTANCE = new ComponentLocator();

    private Map<Class<?>, Object> components;

    public ComponentLocator() {
        components = new HashMap<>();
        configure();
    }

    public <T> T get(Class<T> type) {
        if (components.get(type) instanceof Class<?>) {
            try {
                return (T) ((Class<?>)components.get(type)).newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
        return (T) components.get(type);
    }

    protected void configure() {
        bind(InMemoryRaidRepository.class).to(RaidRepository.class);
        bind(new RaidControllerProvider(get(RaidRepository.class))).to(RaidControllerProvider.class);
bind(FMLCommonHandler.instance().getMinecraftServerInstance()).to(MinecraftServer.class);
        bind(new MinecraftBroadcastLogger(get(MinecraftServer.class))).to(MinecraftBroadcastLogger.class);
    }

    private innerIntermediate bind(Object type) {
        return new innerIntermediate(type);
    }
    private class innerIntermediate {
        private Object type;

        public innerIntermediate(Object type) {
            this.type = type;
        }

        public void to(Class<?> abstraction) {
            components.put(abstraction,type);
        }
    }
}
