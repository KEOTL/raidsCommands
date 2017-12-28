package com.lambdanum.raids.controller;

import com.lambdanum.raids.model.Position;
import com.lambdanum.raids.model.Region;

public class Raid {

    public final String name;
    public final int backupDimension;
    public final Region region;
    public final Position spawn;

    public Raid(String name, int backupDimension, Region region, Position spawn) {
        this.name = name;
        this.backupDimension = backupDimension;
        this.region = region;
        this.spawn = spawn;
    }
}
