package com.lambdanum.raids.controller;

import static junit.framework.TestCase.assertFalse;

import com.lambdanum.raids.model.Region;

import org.junit.Before;
import org.junit.Test;

public class RaidControllerTest {

    private RaidController raidController;

    private static final int DIMENSION = 3;
    private static final String RAID_NAME = "raid";
    private static final Region REGION = new Region();

    @Before
    public void initializeRaidsController() {
        raidController = new RaidController(new Raid(RAID_NAME, DIMENSION, REGION, spawn), DIMENSION);
    }

    @Test
    public void givenNewRaidController_whenCheckingIsRaidActive_thenRaidIsNotActive() {
        boolean isRaidActive = raidController.isRaidActive();

        assertFalse(isRaidActive);
    }


}
