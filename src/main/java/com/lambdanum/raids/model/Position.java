package com.lambdanum.raids.model;

import net.minecraft.util.math.BlockPos;

public class Position {

    public static final Position ORIGIN = new Position(0,0,0);

    public final int x;
    public final int y;
    public final int z;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position() {
        this(0,0,0);
    }

    public Position(BlockPos blockPos) {
        this.x = blockPos.getX();
        this.y = blockPos.getY();
        this.z = blockPos.getZ();
    }
}
