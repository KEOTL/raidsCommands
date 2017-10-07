package com.lambdanum.raids.model;

public class Region {

    private Position[] corners;

    public Region() {
    }

    public Region(Position[] corners) {
        this.corners = corners;
    }

    public Position[] getCorners() {
        return corners;
    }
}
