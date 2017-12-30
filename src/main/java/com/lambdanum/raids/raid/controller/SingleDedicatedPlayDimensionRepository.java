package com.lambdanum.raids.raid.controller;

public class SingleDedicatedPlayDimensionRepository implements RaidPlayDimensionRepository {
    @Override
    public int getAvailablePlayDimension() {
        return 30001;
    }
}
