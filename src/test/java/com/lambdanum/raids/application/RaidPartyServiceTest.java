package com.lambdanum.raids.application;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lambdanum.raids.raid.controller.party.Party;
import com.lambdanum.raids.raid.controller.party.RaidPartyRepository;

import net.minecraft.entity.player.EntityPlayer;
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
    private OnlinePlayerService onlinePlayerService;
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

    @Test
    public void whenRemovingPlayer_thenSendsThePlayerHome() {
        EntityPlayer player = mock(EntityPlayer.class);
        when(onlinePlayerService.getPlayerByUsername(PLAYER_NAME)).thenReturn(player);
        when(partyRepository.getPlayerParty(PLAYER_NAME)).thenReturn(mock(Party.class));

        raidPartyService.removePlayerFromTheirParty(PLAYER_NAME);

        verify(homeService).teleportPlayerToHome(player);
    }
}
