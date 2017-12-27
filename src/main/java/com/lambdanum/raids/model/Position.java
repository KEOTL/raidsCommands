package com.lambdanum.raids.model;

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
}
