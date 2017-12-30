package com.lambdanum.raids.application;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lambdanum.raids.loot.LootTableProvider;
import com.lambdanum.raids.model.Raid;
import com.lambdanum.raids.raid.controller.party.Party;
import com.lambdanum.raids.raid.controller.party.loot.TeamLootStrategy;
import com.lambdanum.raids.raid.controller.party.loot.TeamLootStrategyProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LootServiceTest {

    @Mock
    private LootTableProvider lootTableProvider;
    @Mock
    private OnlinePlayerService onlinePlayerService;
    @Mock
    private TeamLootStrategyProvider lootStrategyProvider;

    @InjectMocks
    private LootService lootService;

    @Mock
    private Raid someRaid;
    @Mock
    private TeamLootStrategy someLootStrategy;
    @Mock
    private Party someParty;

    @Test
    public void whenDistributingTeamLoot_thenLootStrategyDistributesLoot() {
        when(lootStrategyProvider.getLootingStrategy(someRaid)).thenReturn(someLootStrategy);

        lootService.distributeTeamLoot(someParty, someRaid);

        verify(someLootStrategy).distributeLoot(someParty, someRaid);
    }
}
