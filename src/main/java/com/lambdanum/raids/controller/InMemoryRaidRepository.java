package com.lambdanum.raids.controller;

import com.lambdanum.raids.model.Position;
import com.lambdanum.raids.model.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRaidRepository implements RaidRepository {

    private Map<String, Raid> raids = new ConcurrentHashMap<>();

    public InMemoryRaidRepository() {
        initializeSampleData();
    }

    private void initializeSampleData() {
        raids.put("raid1", new Raid("raid1", 30000, new Region(new Position(0,0,0), new Position(10,100,10)), new Position(0,100,0)));
    }

    @Override
    public Raid find(String raidName) {
        return raids.get(raidName);
    }

    @Override
    public List<Raid> getRaids() {
        return new ArrayList<>(raids.values());
    }
}
