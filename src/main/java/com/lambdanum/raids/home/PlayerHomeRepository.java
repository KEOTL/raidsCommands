package com.lambdanum.raids.home;

import com.lambdanum.raids.model.Position;

public interface PlayerHomeRepository {

    Position getPlayerHome(String playerName);

    void setPlayerHome(String playerName, Position playerHome);
}
