package com.lambdanum.raids.raid.controller;

import com.lambdanum.raids.infrastructure.injection.ComponentLocator;
import com.lambdanum.raids.infrastructure.injection.McLogger;
import com.lambdanum.raids.raid.controller.party.RaidPartyRepository;

import java.util.Map;

public class RaidControllerWatchdog implements Runnable {

    private McLogger logger = ComponentLocator.INSTANCE.get(McLogger.class);

    private Map<Integer, RaidController> raidControllers;

    private volatile boolean exit = false;

    public RaidControllerWatchdog(Map<Integer, RaidController> raidControllers) {
        this.raidControllers = raidControllers;
    }

    public void stop() {
        exit = true;
    }

    @Override
    public void run() {
        while (!exit) {
            clearInactiveRaidControllers();
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                // Ignored
            }
        }
    }

    private void clearInactiveRaidControllers() {
        for (Integer dimension : raidControllers.keySet()) {
            RaidController controller = raidControllers.get(dimension);
            if (!controller.isRaidActive()) {
                controller.stop();
                raidControllers.remove(dimension);
                logger.log(String.format("cleared inactive raid %s on dimension %d", controller.getRaidName(), dimension));
            } else {
                RaidPartyRepository raidPartyRepository = ComponentLocator.INSTANCE.get(RaidPartyRepository.class);
                controller.resynchronizePartyWithRepository(raidPartyRepository);
            }
        }
    }
}
