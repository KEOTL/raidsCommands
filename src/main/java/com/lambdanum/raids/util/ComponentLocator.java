package com.lambdanum.raids.util;

import com.lambdanum.raids.controller.RaidControllerFactory;
import com.lambdanum.raids.controller.RaidRepository;
import com.lambdanum.raids.controller.RestRaidRepository;

import java.util.HashMap;
import java.util.Map;

public class ComponentLocator {

    public static final ComponentLocator INSTANCE = new ComponentLocator();

    private Map<Class<?>, Object> components;

    public ComponentLocator() {
        components = new HashMap<>();
        configure();
    }

    public Object getComponent(Class<?> type) {
        if (components.get(type) instanceof Class<?>) {
            try {
                return ((Class) components.get(type)).newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
        return components.get(type);
    }

    protected void configure() {
        bind(RaidControllerFactory.class).to(RaidControllerFactory.class);
        bind(RestRaidRepository.class).to(RaidRepository.class);

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
