package com.lambdanum.raids.raid.controller.party.loot;

import com.lambdanum.raids.loot.LootTableProvider;
import com.lambdanum.raids.model.LootItem;
import com.lambdanum.raids.model.Raid;
import com.lambdanum.raids.raid.controller.party.Party;

import java.util.List;

public class IdenticalTeamLootStrategy implements TeamLootStrategy {

    private LootTableProvider lootTableProvider;

    public IdenticalTeamLootStrategy(LootTableProvider lootTableProvider) {
        this.lootTableProvider = lootTableProvider;
    }

    @Override
    public void distributeLoot(Party party, Raid raid) {
        List<LootItem> loot = lootTableProvider.roll(raid.rewardLootTableName, "raidPlayer");
        for (String player : party.getPlayers()) {
            loot.forEach(item -> party.giveToPlayer(player, item));
        }
    }
}
