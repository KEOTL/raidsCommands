package com.lambdanum.raids.application;

import com.lambdanum.raids.loot.LootTableProvider;
import com.lambdanum.raids.model.LootItem;
import com.lambdanum.raids.model.Raid;
import com.lambdanum.raids.raid.controller.party.Party;
import com.lambdanum.raids.raid.controller.party.loot.TeamLootStrategy;
import com.lambdanum.raids.raid.controller.party.loot.TeamLootStrategyProvider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class LootService {

    private LootTableProvider lootTableProvider;
    private OnlinePlayerService onlinePlayerService;
    private TeamLootStrategyProvider lootStrategyProvider;

    public LootService(LootTableProvider lootTableProvider, OnlinePlayerService onlinePlayerService, TeamLootStrategyProvider lootStrategyProvider) {
        this.lootTableProvider = lootTableProvider;
        this.onlinePlayerService = onlinePlayerService;
        this.lootStrategyProvider = lootStrategyProvider;
    }

    public void asyncDistributeLoot(String playerName, String tableName) {
        new Thread(() -> distributeLoot(playerName, tableName)).start();
    }

    public void distributeLoot(String playerName, String tableName) {
        EntityPlayer player = onlinePlayerService.getPlayerByUsername(playerName);
        for (LootItem item : lootTableProvider.roll(tableName, player.getName())) {
            player.addItemStackToInventory(new ItemStack(Item.getByNameOrId(item.minecraftId), item.amount));
        }
    }

    public void distributeTeamLoot(Party party, Raid raid) {
        TeamLootStrategy lootingStrategy = lootStrategyProvider.getLootingStrategy(raid);
        lootingStrategy.distributeLoot(party, raid);
    }

    public void asyncDistributeTeamLoot(Party party, Raid raid) {
        new Thread(() -> distributeTeamLoot(party, raid)).start();
    }
}
