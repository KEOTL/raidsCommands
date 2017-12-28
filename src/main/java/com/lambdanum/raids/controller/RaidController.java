package com.lambdanum.raids.controller;

import com.lambdanum.raids.util.ComponentLocator;
import com.lambdanum.raids.util.MinecraftBroadcastLogger;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class RaidController implements ConditionObserver {

    private final MinecraftBroadcastLogger logger = ComponentLocator.INSTANCE.get(MinecraftBroadcastLogger.class);
    private final RegionCloner regionCloner = ComponentLocator.INSTANCE.get(RegionCloner.class);

    private Raid raid;
    private final int dimension;

    private boolean active = true;

    public RaidController(Raid raid, int playDimension) {
        this.raid = raid;
        this.dimension = playDimension;

        System.out.println("Initialized raidController on dimension :" + dimension);
    }

    public boolean isRaidActive() {
        return active;
    }

    public void startMapInitialization(MinecraftServer minecraftServer) {
        new Thread(() -> {
            WorldServer targetWorld = minecraftServer.getWorld(dimension);
            WorldServer cleanRaidMap = minecraftServer.getWorld(raid.backupDimension);
            regionCloner.cloneRegionToOtherDimension(cleanRaidMap, targetWorld, raid.region);
            logger.log("done initializing raid map '" + raid.name + "' on dimension " + dimension);
            active = false;
        }).start();
    }

    @Override
    public void notifyOnWatchedCondition() {
        // Something has happened inside this raid.
    }

    public String getRaidName() {
        return raid.name;
    }
}
