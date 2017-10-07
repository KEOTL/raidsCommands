package com.lambdanum.raids.controller;

import com.lambdanum.raids.model.Region;

public class RaidController {

    private final String raidName;
    private final int dimension;

    private boolean active = false;

    public RaidController(Raid raid) {
        this.raidName = raid.name;
        this.dimension = raid.dimension;

        System.out.println("Initialized raidController on dimension :" + dimension);
    }

    public RaidController(String raidName, int dimension, Region region) {
        this(new Raid(raidName,dimension, region));
    }

    public boolean isRaidActive() {
        return active;
    }


}
