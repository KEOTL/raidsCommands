package com.lambdanum.raids.model;

public class Raid {

    public final String name;
    public final int backupDimension;
    public final Region region;
    public final Position spawn;
    public String[] startupScript;

    public Raid(String name, int backupDimension, Region region, Position spawn, String... startupScript) {
        this.name = name;
        this.backupDimension = backupDimension;
        this.region = region;
        this.spawn = spawn;
        this.startupScript = startupScript;
    }
}
