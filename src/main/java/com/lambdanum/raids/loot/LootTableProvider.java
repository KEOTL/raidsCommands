package com.lambdanum.raids.loot;

import com.lambdanum.raids.model.LootItem;

import java.util.List;

public interface LootTableProvider {

    List<LootItem> roll(String tableName, String playerName);
}
