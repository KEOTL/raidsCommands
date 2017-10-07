package com.lambdanum.raids.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryRaidRepository implements RaidRepository {

    private Map<String, Integer> raids = new HashMap<>();

    public InMemoryRaidRepository() {
        raids.put("test", 30001);
    }

    @Override
    public List<RaidDto> getRaids() {
        return raids.keySet().stream().map(name -> new RaidDto(name,raids.get(name))).collect(Collectors.toList());
    }
}
