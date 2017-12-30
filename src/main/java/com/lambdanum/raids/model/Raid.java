package com.lambdanum.raids.model;

import com.lambdanum.raids.raid.controller.party.loot.TeamLootType;

import java.util.List;

public class Raid {

    public final String name;
    public final int backupDimension;
    public final Region region;
    public final Position spawn;
    public List<String> startupScript;
    public final List<String> victoryScript;
    public final List<String> defeatScript;
    public final String rewardLootTableName;
    public final TeamLootType lootStrategy;

    public Raid(String name, int backupDimension, Region region, Position spawn, String rewardLootTableName, TeamLootType lootStrategy, List<String> startupScript, List<String> victoryScript, List<String> defeatScript) {
        this.name = name;
        this.backupDimension = backupDimension;
        this.region = region;
        this.spawn = spawn;
        this.rewardLootTableName = rewardLootTableName;
        this.lootStrategy = lootStrategy;
        this.startupScript = startupScript;
        this.victoryScript = victoryScript;
        this.defeatScript = defeatScript;
    }
}
