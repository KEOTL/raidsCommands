package com.lambdanum.raids.raid.controller;

import com.lambdanum.raids.model.Raid;

public interface RaidRepository {

    Raid find(String raidName);
}
