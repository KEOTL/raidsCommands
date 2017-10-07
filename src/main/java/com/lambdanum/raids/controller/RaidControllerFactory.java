package com.lambdanum.raids.controller;

import java.util.HashMap;
import java.util.Map;

public class RaidControllerFactory {

    private Map<String, RaidController> raidControllers;
    private RaidRepository raidRepository;

    public RaidControllerFactory(RaidRepository raidRepository) {
        this.raidRepository = raidRepository;
        raidControllers = new HashMap<>();
        populateControllers();
    }

    private void populateControllers() {
        raidRepository.getRaids().forEach(raid -> raidControllers.put(raid.name, new RaidController(raid)));
    }

    public RaidController getRaidController(String raidName) {
        return raidControllers.get(raidName);
    }
}
