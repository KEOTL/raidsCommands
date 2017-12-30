package com.lambdanum.raids.context;

import com.lambdanum.raids.infrastructure.injection.AbstractBinder;
import com.lambdanum.raids.infrastructure.persistence.DummyLootTableProvider;
import com.lambdanum.raids.loot.LootTableProvider;
import com.lambdanum.raids.raid.controller.party.loot.IdenticalTeamLootStrategy;
import com.lambdanum.raids.raid.controller.party.loot.IndividualTeamLootStrategy;
import com.lambdanum.raids.raid.controller.party.loot.SharedTeamLootStrategy;
import com.lambdanum.raids.raid.controller.party.loot.TeamLootStrategyProvider;

public class LootingBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(DummyLootTableProvider.class).to(LootTableProvider.class);

        bind(SharedTeamLootStrategy.class).to(SharedTeamLootStrategy.class);
        bind(IdenticalTeamLootStrategy.class).to(IdenticalTeamLootStrategy.class);
        bind(IndividualTeamLootStrategy.class).to(IndividualTeamLootStrategy.class);
        bind(TeamLootStrategyProvider.class).to(TeamLootStrategyProvider.class);
    }
}
