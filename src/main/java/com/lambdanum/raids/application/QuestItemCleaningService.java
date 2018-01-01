package com.lambdanum.raids.application;

import com.lambdanum.raids.minecraft.OnlinePlayerRepository;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class QuestItemCleaningService {

    private static final int OVERWORLD = 0;

    private OnlinePlayerRepository onlinePlayerRepository;

    public QuestItemCleaningService(OnlinePlayerRepository onlinePlayerRepository) {
        this.onlinePlayerRepository = onlinePlayerRepository;
    }

    public void clearQuestItemsInInventoryOfPlayersInTheOverworld() {
        for (EntityPlayer player : onlinePlayerRepository.getPlayersInDimension(OVERWORLD)) {
            clearQuestItemsFromInventory(player.inventory.mainInventory);
            clearQuestItemsFromInventory(player.inventory.offHandInventory);
            clearQuestItemsFromInventory(player.inventory.armorInventory);
        }
    }

    private void clearQuestItemsFromInventory(List<ItemStack> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack itemStack = inventory.get(i);
            if (hasQuestItemLoreTag(itemStack)) {
                inventory.set(i, ItemStack.EMPTY);
            }
        }
    }

    private boolean hasQuestItemLoreTag(ItemStack itemStack) {
        try {
            return itemStack.getSubCompound("display").getTag("Lore").toString().contains("Quest Item");
        } catch (NullPointerException e) {
            return false;
        }
    }


}
