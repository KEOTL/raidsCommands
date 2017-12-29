package com.lambdanum.raids.infrastructure.injection;

import com.lambdanum.raids.context.MainBinder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ComponentLocator {

    public static ComponentLocator INSTANCE = new ComponentLocator();

    private Map<Class<?>, Object> components;

    public ComponentLocator() {
        components = new HashMap<>();
        configure();
    }

    public <T> T get(Class<T> type) {
        if (!components.containsKey(type)) {
            throw new InjectionException("could not find component " + type.getName());
        }
        Object storedObject = components.get(type);
        if (storedObject instanceof RuntimeProvider) {
            return (T) ((RuntimeProvider) storedObject).get();
        }
        if (!(storedObject instanceof Class<?>)) {
            return (T) storedObject;
        }
        try {
            ArrayList<Object> constructorDependencies = new ArrayList<>();
            for (Class<?> constructorParameter : ((Class<?>) storedObject).getDeclaredConstructors()[0].getParameterTypes()) {
                constructorDependencies.add(get(constructorParameter));
            }
            return (T) ((Class<?>) storedObject).getDeclaredConstructors()[0].newInstance(constructorDependencies.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public <T> List<T> getAllChildren(Class<? extends T> clazz) {
        List<T> children = new LinkedList<>();
        for (Class<?> type : components.keySet()) {
            if (clazz.isAssignableFrom(type)) {
                children.add(get((Class<T>) type));
            }
        }
        return children;
    }

    protected void configure() {
        install(MainBinder.class);
    }

    protected innerIntermediate bind(Object type) {
        return new innerIntermediate(type);
    }

    public class innerIntermediate {
        private Object type;

        public innerIntermediate(Object type) {
            this.type = type;
        }

        public void to(Class<?> abstraction) {
            components.put(abstraction, type);
        }
    }

    protected void install(ComponentLocator componentLocator) {
        components.putAll(componentLocator.components);
    }

    protected void install(Class<? extends ComponentLocator> componentLocatorClass) {
        if (components.containsKey(componentLocatorClass)) {
            install(get(componentLocatorClass));
        } else {
            try {
                install(componentLocatorClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Could not instantiate binder");
            }
        }
    }
}
