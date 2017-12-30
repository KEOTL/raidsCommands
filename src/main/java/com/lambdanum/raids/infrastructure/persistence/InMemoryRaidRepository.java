package com.lambdanum.raids.infrastructure.persistence;

import com.lambdanum.raids.model.Position;
import com.lambdanum.raids.model.Raid;
import com.lambdanum.raids.model.Region;
import com.lambdanum.raids.raid.controller.RaidRepository;
import com.lambdanum.raids.raid.controller.party.loot.TeamLootType;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRaidRepository implements RaidRepository {

    private Map<String, Raid> raids = new ConcurrentHashMap<>();

    public InMemoryRaidRepository() {
        initializeSampleData();
    }

    private void initializeSampleData() {
        raids.put("raid1", new Raid("raid1", 30000, new Region(new Position(0,0,0), new Position(10,100,10)), new Position(0,100,0),
            "daily", TeamLootType.SHARED,
            Arrays.asList("time set 0", "objective ask-item minecraft:chest 0 62 0 ?? echo raid is pleased ;; victory", "say welcome!"),
            Arrays.asList("you won!"), Arrays.asList("you lost :("), 1, 10));
        raids.put("raid2", new Raid("raid2", 30000, new Region(new Position(0,0,0), new Position(10,100,10)), new Position(0,100,0),
            "daily", TeamLootType.SHARED,
            Arrays.asList("time set 0", "objective bring-player 5 62 5 ?? say A hero has come! ;; victory", "say welcome!"),
            Arrays.asList("say you won!"), Arrays.asList("say you lost :("), 2, 10));
    }

    @Override
    public Raid find(String raidName) {
        return raids.get(raidName);
    }
}
