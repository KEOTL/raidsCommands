package com.lambdanum.raids.controller;

import com.lambdanum.raids.model.Region;

public class Raid {

    public final String name;
    public final int dimension;
    public final Region region;

    public Raid(String name, int dimension, Region region) {
        this.name = name;
        this.dimension = dimension;
        this.region = region;
    }
}
