package com.lambdanum.raids.controller;

public class RaidController {

    private final String raidName;
    private final int dimension;

    private boolean active = false;

    public RaidController(RaidDto raidDto) {
        this.raidName = raidDto.name;
        this.dimension = raidDto.dimension;

        System.out.println("Initialized raidController on dimension :" + dimension);
    }

    public RaidController(String raidName, int dimension) {
        this(new RaidDto(raidName,dimension));
    }

    public boolean isRaidActive() {
        return active;
    }


}
