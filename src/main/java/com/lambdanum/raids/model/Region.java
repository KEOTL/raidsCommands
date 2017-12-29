package com.lambdanum.raids.model;

public class Region {

    public final Position lowerCorner;
    public final Position higherCorner;

    public Region(Position lowerCorner, Position higherCorner) {
        this.lowerCorner = lowerCorner;
        this.higherCorner = higherCorner;
    }
}
