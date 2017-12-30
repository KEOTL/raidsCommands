package com.lambdanum.raids.infrastructure.utils.minecraft;

public class SystemOutputLogger implements DebugMcLogger {
    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
