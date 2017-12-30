package com.lambdanum.raids.raid.controller.party.loot;

import com.lambdanum.raids.model.Raid;

public class TeamLootStrategyProvider {

    private SharedTeamLootStrategy sharedTeamLootStrategy;
    private IndividualTeamLootStrategy individualTeamLootStrategy;
    private IdenticalTeamLootStrategy identicalTeamLootStrategy;

    public TeamLootStrategyProvider(SharedTeamLootStrategy sharedTeamLootStrategy, IndividualTeamLootStrategy individualTeamLootStrategy, IdenticalTeamLootStrategy identicalTeamLootStrategy) {
        this.sharedTeamLootStrategy = sharedTeamLootStrategy;
        this.individualTeamLootStrategy = individualTeamLootStrategy;
        this.identicalTeamLootStrategy = identicalTeamLootStrategy;
    }

    public TeamLootStrategy getLootingStrategy(Raid raid) {
        switch (raid.lootStrategy) {
            case SHARED:
                return sharedTeamLootStrategy;
            case IDENTICAL:
                return identicalTeamLootStrategy;
            case ROLL_INDIVIDUALLY:
                return individualTeamLootStrategy;
        }
        throw new UnknownLootingStrategyException();
    }
}
