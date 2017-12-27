package com.lambdanum.raids.controller;

import com.lambdanum.raids.model.Position;
import com.lambdanum.raids.model.Region;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRaidRepository implements RaidRepository {

    private List<Raid> raids = new ArrayList<>();

    public InMemoryRaidRepository() {
        raids.add(new Raid("raid1", 30000, new Region(new Position(0,0,0), new Position(10,100,10))));
    }

    @Override
    public List<Raid> getRaids() {
        return raids;
    }
}
