package com.lambdanum.raids.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RaidControllerFactoryTest {

    private RaidControllerFactory raidControllerFactory;

    @Mock
    private RaidRepository raidRepository;

    private static final String RAID_NAME = "raid";
    private static final int RAID_DIMENSION = 5;

    @Before
    public void setupRaidControllerFactory() {
        when(raidRepository.getRaids()).thenReturn(Collections.singletonList(new RaidDto(RAID_NAME, RAID_DIMENSION)));
        raidControllerFactory = new RaidControllerFactory(raidRepository);
    }

    @Test
    public void givenASingleRaid_whenCreatingTheRaidControllerFactory_thenACorrespondingRaidControllerIsCreated() {
        RaidController controller = raidControllerFactory.getRaidController(RAID_NAME);

        assertTrue(controller != null);
    }


}