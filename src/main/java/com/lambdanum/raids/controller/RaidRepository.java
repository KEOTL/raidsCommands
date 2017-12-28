package com.lambdanum.raids.controller;

import java.util.List;

public interface RaidRepository {

    Raid find(String raidName);
    List<Raid> getRaids();

}
