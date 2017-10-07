package com.lambdanum.raids.controller;

import com.lambdanum.raids.model.Region;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRaidRepository implements RaidRepository {

    private List<Raid> raids = new ArrayList<>();

    public InMemoryRaidRepository() {
        raids.add(new Raid("test", 30001, new Region()));
    }

    @Override
    public List<Raid> getRaids() {
        return raids;
    }
}
