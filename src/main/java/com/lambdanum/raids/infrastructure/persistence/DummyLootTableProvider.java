package com.lambdanum.raids.infrastructure.persistence;

import com.lambdanum.raids.loot.LootTableProvider;
import com.lambdanum.raids.model.LootItem;

import java.util.Collections;
import java.util.List;

public class DummyLootTableProvider implements LootTableProvider {

    @Override
    public List<LootItem> roll(String tableName, String playerName) {
        return Collections.singletonList(new LootItem("minecraft:grass", 2));
    }
}
