package com.lambdanum.raids.controller;

import com.lambdanum.raids.model.Position;

import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class RaidController {

    private Raid raid;
    private final int dimension;

    private boolean active = false;

    public RaidController(Raid raid, int playDimension) {
        this.raid = raid;
        this.dimension = playDimension;

        System.out.println("Initialized raidController on dimension :" + dimension);
    }

    public boolean isRaidActive() {
        return active;
    }

    public void initializePlayDimensionMap(MinecraftServer minecraftServer) {
        WorldServer targetWorld = minecraftServer.getWorld(dimension);
        WorldServer cleanRaidMap = minecraftServer.getWorld(raid.backupDimension);

        Position lowerCorner = raid.region.getCorners()[0];
        Position higherCorner = raid.region.getCorners()[1];

        for (int i = lowerCorner.getX(); i < higherCorner.getX(); i++) {
            for (int j = lowerCorner.getY(); j < higherCorner.getY(); j++) {
                for (int k = lowerCorner.getZ(); k < higherCorner.getZ(); k++) {
                    BlockPos clonedPosition = new BlockPos(i,j,k);
                    IBlockState sourceBlock = cleanRaidMap.getBlockState(clonedPosition);
                    targetWorld.setBlockState(clonedPosition, sourceBlock);
                }
            }
        }


    }

}
