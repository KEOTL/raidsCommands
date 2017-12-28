package com.lambdanum.raids.infrastructure.utils.minecraft;

import com.lambdanum.raids.model.Position;
import com.lambdanum.raids.model.Region;
import com.lambdanum.raids.infrastructure.ComponentLocator;
import com.lambdanum.raids.infrastructure.McLogger;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class RegionCloner {

    private final McLogger logger = ComponentLocator.INSTANCE.get(McLogger.class);
    private MinecraftServer minecraftServer;

    public RegionCloner(MinecraftServer minecraftServer) {
        this.minecraftServer = minecraftServer;
    }

    public void cloneRegionToOtherDimension(WorldServer sourceWorld, WorldServer targetWorld, Region sourceRegion) {
        killEntitiesInDestination(sourceRegion);

        Position lowerCorner = sourceRegion.lowerCorner;
        Position higherCorner = sourceRegion.higherCorner;

        for (int i = lowerCorner.x; i < higherCorner.x; i++) {
            for (int j = lowerCorner.y; j < higherCorner.y; j++) {
                for (int k = lowerCorner.z; k < higherCorner.z; k++) {
                    BlockPos clonedPosition = new BlockPos(i,j,k);
                    IBlockState sourceBlock = sourceWorld.getBlockState(clonedPosition);
                    targetWorld.setBlockState(clonedPosition, sourceBlock);
                    try {
                        TileEntity clone = clone(sourceWorld.getTileEntity(clonedPosition));
                        targetWorld.setTileEntity(clonedPosition, clone);
                    } catch (NullPointerException e) {
                        // Ignored
                    } catch (IllegalAccessException | InstantiationException e) {
                        logger.log(e.toString());
                        e.printStackTrace();
                    }
                }
            }
        }
        logger.log("done cloning");
    }

    private void killEntitiesInDestination(Region region) {
        String command = String.format("kill @e[x=%d,y=%d,z=%d,dx=%d,dy=%d,dz=%d]", region.lowerCorner.x, region.lowerCorner.y, region.lowerCorner.z,
            region.higherCorner.x - region.lowerCorner.x, region.higherCorner.y - region.lowerCorner.y,
            region.higherCorner.z - region.lowerCorner.z);
        logger.log("killing entities in destination");
        logger.log(command);
        minecraftServer.commandManager.executeCommand(minecraftServer, command);
    }

    private TileEntity clone(TileEntity source) throws IllegalAccessException, InstantiationException {
        TileEntity clone = source.getClass().newInstance();
        NBTTagCompound nbtData = source.serializeNBT();
        clone.deserializeNBT(nbtData);
        clone.markDirty();
        return clone;
    }
}
