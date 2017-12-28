package com.lambdanum.raids.application;

import com.lambdanum.raids.loot.LootTableProvider;
import com.lambdanum.raids.model.LootItem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class LootService {

    private LootTableProvider lootTableProvider;
    private OnlinePlayerService onlinePlayerService;

    public LootService(LootTableProvider lootTableProvider, OnlinePlayerService onlinePlayerService) {
        this.lootTableProvider = lootTableProvider;
        this.onlinePlayerService = onlinePlayerService;
    }

    public void asyncLoot(String playerName, String tableName) {
        new Thread(() -> {
            EntityPlayer player = onlinePlayerService.getPlayerByUsername(playerName);
            for (LootItem item : lootTableProvider.roll(tableName, player.getName())) {
                player.addItemStackToInventory(new ItemStack(Item.getByNameOrId(item.minecraftId),item.amount));
            }
        }).start();
    }
}
