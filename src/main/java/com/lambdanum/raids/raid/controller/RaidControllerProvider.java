package com.lambdanum.raids.raid.controller;

import com.lambdanum.raids.model.Raid;
import com.lambdanum.raids.raid.controller.party.Party;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RaidControllerProvider {

    private Map<Integer, RaidController> raidControllers;
    private RaidRepository raidRepository;
    private Thread watchdog;

    public RaidControllerProvider(RaidRepository raidRepository) {
        this.raidRepository = raidRepository;
        raidControllers = new ConcurrentHashMap<>();
        watchdog = new Thread(new RaidControllerWatchdog(raidControllers));
        watchdog.start();
    }

    public RaidController createController(String raidName, int playDimension, Party party) {
        Raid raid = raidRepository.find(raidName);
        RaidController controller = new RaidController(raid, playDimension, party);
        raidControllers.put(playDimension, controller);
        return controller;
    }

    public boolean isRaidActiveInDimension(int playDimension) {
        return raidControllers.containsKey(playDimension);
    }

    public RaidController getController(int playDimension) {
        return raidControllers.get(playDimension);
    }
}
