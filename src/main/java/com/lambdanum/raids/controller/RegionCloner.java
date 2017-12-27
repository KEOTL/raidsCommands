package com.lambdanum.raids.controller;

import com.lambdanum.raids.model.Position;
import com.lambdanum.raids.model.Region;
import com.lambdanum.raids.util.ComponentLocator;
import com.lambdanum.raids.util.MinecraftBroadcastLogger;

import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class RegionCloner {

    private final MinecraftBroadcastLogger logger = ComponentLocator.INSTANCE.get(MinecraftBroadcastLogger.class);
    private MinecraftServer minecraftServer;

    public RegionCloner(MinecraftServer minecraftServer) {
        this.minecraftServer = minecraftServer;
    }

    public void cloneRegionToOtherDimension(WorldServer sourceWorld, WorldServer targetWorld, Region sourceRegion) {
        Position lowerCorner = sourceRegion.lowerCorner;
        Position higherCorner = sourceRegion.higherCorner;

        for (int i = lowerCorner.x; i < higherCorner.x; i++) {
            for (int j = lowerCorner.y; j < higherCorner.y; j++) {
                for (int k = lowerCorner.z; k < higherCorner.z; k++) {
                    BlockPos clonedPosition = new BlockPos(i,j,k);
                    IBlockState sourceBlock = sourceWorld.getBlockState(clonedPosition);
                    targetWorld.setBlockState(clonedPosition, sourceBlock);
                }
            }
        }
        logger.log("done cloning");
    }
}
