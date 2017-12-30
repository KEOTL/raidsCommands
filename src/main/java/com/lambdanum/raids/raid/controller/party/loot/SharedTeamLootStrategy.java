package com.lambdanum.raids.raid.controller.party.loot;

import com.lambdanum.raids.loot.LootTableProvider;
import com.lambdanum.raids.model.LootItem;
import com.lambdanum.raids.model.Raid;
import com.lambdanum.raids.raid.controller.party.Party;

import java.util.List;
import java.util.Random;

public class SharedTeamLootStrategy implements TeamLootStrategy {

    private LootTableProvider lootTableProvider;

    public SharedTeamLootStrategy(LootTableProvider lootTableProvider) {
        this.lootTableProvider = lootTableProvider;
    }

    @Override
    public void distributeLoot(Party party, Raid raid) {
        List<LootItem> loot = lootTableProvider.roll(raid.rewardLootTableName, "raidPlayer");
        List<String> players = party.getPlayers();

        Random random = new Random();
        for (LootItem lootItem : loot) {
            String luckyPlayer = players.get(random.nextInt(players.size()));
            party.giveToPlayer(luckyPlayer, lootItem);
        }
    }
}
