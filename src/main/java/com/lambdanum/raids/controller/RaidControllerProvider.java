package com.lambdanum.raids.controller;

import java.util.HashMap;
import java.util.Map;

public class RaidControllerProvider {

    public static final int PLAY_DIMENSION = 0;
    private Map<String, RaidController> raidControllers;
    private RaidRepository raidRepository;

    public RaidControllerProvider(RaidRepository raidRepository) {
        this.raidRepository = raidRepository;
        raidControllers = new HashMap<>();
        populateControllers();
    }

    private void populateControllers() {
        raidRepository.getRaids().forEach(raid -> raidControllers.put(raid.name, new RaidController(raid, PLAY_DIMENSION)));
    }

    public RaidController getRaidController(String raidName) {
        return raidControllers.get(raidName);
    }
}
