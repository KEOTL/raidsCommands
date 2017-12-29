package com.lambdanum.raids.skyblock;

public interface SkyblockUserRepository {

    boolean hasUserAlreadyGeneratedIsland(String playerName);

    void setUserHasAlreadyGeneratedIsland(String playerName);
}
