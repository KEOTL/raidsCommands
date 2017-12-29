package com.lambdanum.raids.skyblock;

import com.lambdanum.raids.model.Position;

import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;

public class IslandGenerationStrategy {

    private MinecraftServer minecraftServer;

    public IslandGenerationStrategy(MinecraftServer minecraftServer) {
        this.minecraftServer = minecraftServer;
    }

    public void generateIsland(Position playerPos) {
        for (int i = 0; i < 6; i++) {
            minecraftServer.getWorld(0).setBlockState(playerPos.add(i - 3, -1, 0).toBlockPos(), Blocks.GRASS.getDefaultState());
            minecraftServer.getWorld(0).setBlockState(playerPos.add(i - 3, -1, -1).toBlockPos(), Blocks.GRASS.getDefaultState());
            minecraftServer.getWorld(0).setBlockState(playerPos.add(i - 3, -1, 1).toBlockPos(), Blocks.GRASS.getDefaultState());
            for (int j = 1; j < 3; j++) {
                minecraftServer.getWorld(0).setBlockState(playerPos.add(i - 3, -1 - j, 0).toBlockPos(), Blocks.DIRT.getDefaultState());
                minecraftServer.getWorld(0).setBlockState(playerPos.add(i - 3, -1 - j, -1).toBlockPos(), Blocks.DIRT.getDefaultState());
                minecraftServer.getWorld(0).setBlockState(playerPos.add(i - 3, -1 - j, 1).toBlockPos(), Blocks.DIRT.getDefaultState());
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    minecraftServer.getWorld(0).setBlockState(playerPos.add(2-i,2+j,1-k).toBlockPos(),Blocks.LEAVES.getDefaultState());
                }
            }
        }

        for (int j = 0; j < 5; j++) {
            minecraftServer.getWorld(0).setBlockState(playerPos.add(1, j, 0).toBlockPos(), Blocks.LOG.getDefaultState());
        }
    }
}
