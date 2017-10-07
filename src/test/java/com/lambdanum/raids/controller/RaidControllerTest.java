package com.lambdanum.raids.controller;

import static junit.framework.TestCase.assertFalse;

import org.junit.Before;
import org.junit.Test;

public class RaidControllerTest {

    private RaidController raidController;

    private static final int DIMENSION = 3;
    private static final String RAID_NAME = "raid";

    @Before
    public void initializeRaidsController() {
        raidController = new RaidController(RAID_NAME, DIMENSION);
    }

    @Test
    public void givenNewRaidController_whenCheckingIsRaidActive_thenRaidIsNotActive() {
        boolean isRaidActive = raidController.isRaidActive();

        assertFalse(isRaidActive);
    }


}
