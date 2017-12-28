package com.lambdanum.raids.raid.controller;

import javax.annotation.Nullable;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class RaidCommandSender implements ICommandSender {

    private WorldServer worldServer;

    public RaidCommandSender(WorldServer worldServer) {
        this.worldServer = worldServer;
    }

    @Override
    public String getName() {
        return "raid sender on dimension " + worldServer.provider.getDimension();
    }

    @Override
    public boolean canUseCommand(int permLevel, String commandName) {
        return true;
    }

    @Override
    public World getEntityWorld() {
        return worldServer;
    }

    @Nullable
    @Override
    public MinecraftServer getServer() {
        return worldServer.getMinecraftServer();
    }

    public int getDimension() {
        return worldServer.provider.getDimension();
    }
}
