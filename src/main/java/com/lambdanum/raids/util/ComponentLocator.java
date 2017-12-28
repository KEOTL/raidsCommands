package com.lambdanum.raids.util;

import com.lambdanum.raids.commands.StartRaidCommand;
import com.lambdanum.raids.controller.InMemoryRaidRepository;
import com.lambdanum.raids.controller.McLogger;
import com.lambdanum.raids.controller.RaidControllerProvider;
import com.lambdanum.raids.controller.RaidControllerWatchdog;
import com.lambdanum.raids.controller.RaidRepository;
import com.lambdanum.raids.controller.RegionCloner;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

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
        bind(RegionCloner.class).to(RegionCloner.class);
        bind(StartRaidCommand.class).to(StartRaidCommand.class);
        bind(MinecraftBroadcastLogger.class).to(McLogger.class);
        bind(RaidControllerWatchdog.class).to(RaidControllerWatchdog.class);
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
