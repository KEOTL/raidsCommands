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

    public Position add(int i, int j, int k) {
        return new Position(x + i, y + j, z + k);
    }

    public BlockPos toBlockPos() {
        return new BlockPos(x,y,z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position)) {
            return false;
        }

        Position position = (Position) o;

        if (x != position.x) {
            return false;
        }
        if (y != position.y) {
            return false;
        }
        return z == position.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }
}
