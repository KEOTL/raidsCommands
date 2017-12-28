package com.lambdanum.raids.raid.controller;

import com.lambdanum.raids.model.Raid;

import java.util.List;

public interface RaidRepository {

    Raid find(String raidName);
    List<Raid> getRaids();

}
