package com.lambdanum.raids.model;

public class LootItem {

    public String minecraftId;
    public int amount;

    public LootItem() {
    }

    public LootItem(String minecraftId, int amount) {
        this.minecraftId = minecraftId;
        this.amount = amount;
    }
}
