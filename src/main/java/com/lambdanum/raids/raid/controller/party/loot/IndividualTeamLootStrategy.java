package com.lambdanum.raids.raid.controller.party.loot;

import com.lambdanum.raids.loot.LootTableProvider;
import com.lambdanum.raids.model.LootItem;
import com.lambdanum.raids.model.Raid;
import com.lambdanum.raids.raid.controller.party.Party;

import java.util.List;

public class IndividualTeamLootStrategy implements TeamLootStrategy {

    private LootTableProvider lootTableProvider;

    public IndividualTeamLootStrategy(LootTableProvider lootTableProvider) {
        this.lootTableProvider = lootTableProvider;
    }

    @Override
    public void distributeLoot(Party party, Raid raid) {
        for (String player : party.getPlayers()) {
            List<LootItem> loot = lootTableProvider.roll(raid.rewardLootTableName, player);
            loot.forEach(item -> party.giveToPlayer(player, item));
        }
    }
}
