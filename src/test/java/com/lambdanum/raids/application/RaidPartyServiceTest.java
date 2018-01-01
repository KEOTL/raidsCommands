package com.lambdanum.raids.application;

import static org.mockito.Mockito.verify;

import com.lambdanum.raids.raid.controller.party.RaidPartyRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RaidPartyServiceTest {

    @Mock
    private RaidPartyRepository partyRepository;
    @Mock
    private OnlinePlayerRepository onlinePlayerRepository;
    @Mock
    private PlayerHomeService homeService;

    @InjectMocks
    private RaidPartyService raidPartyService;

    private static final String PLAYER_NAME = "name";

    @Test
    public void whenCheckingIsPlayerInAParty_thenRaidPartyRepositoryChecksIfPlayerIsInAParty() {
        raidPartyService.isPlayerInAParty(PLAYER_NAME);

        verify(partyRepository).isPlayerInAParty(PLAYER_NAME);
    }
}
