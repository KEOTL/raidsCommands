package com.lambdanum.raids.util;

import com.lambdanum.raids.controller.InMemoryRaidRepository;
import com.lambdanum.raids.controller.RaidControllerProvider;
import com.lambdanum.raids.controller.RaidRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
        if (!(components.get(type) instanceof Class<?>)) {
            return (T) components.get(type);
        }
        try {
            ArrayList<Object> constructorDependencies = new ArrayList<>();
            for (Class<?> constructorParameter : ((Class<?>) components.get(type)).getDeclaredConstructors()[0].getParameterTypes()) {
                constructorDependencies.add(get(constructorParameter));
            }
            return (T) ((Class<?>) components.get(type)).getDeclaredConstructors()[0].newInstance(constructorDependencies.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    protected void configure() {
        bind(InMemoryRaidRepository.class).to(RaidRepository.class);
        bind(RaidControllerProvider.class).to(RaidControllerProvider.class);
        bind(FMLCommonHandler.instance().getMinecraftServerInstance()).to(MinecraftServer.class);
        bind(MinecraftBroadcastLogger.class).to(MinecraftBroadcastLogger.class);
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
            components.put(abstraction, type);
        }
    }
}
