package com.lambdanum.raids.infrastructure.persistence;

import com.lambdanum.raids.HttpHelper;
import com.lambdanum.raids.context.config.ServerProperties;
import com.lambdanum.raids.home.PlayerHomeRepository;
import com.lambdanum.raids.model.Position;

public class RestPlayerHomeRepository implements PlayerHomeRepository {

    private HttpHelper httpHelper;
    private String serverUrl;

    public RestPlayerHomeRepository(HttpHelper httpHelper, ServerProperties serverProperties) {
        this.httpHelper = httpHelper;
        this.serverUrl = serverProperties.serverUrl;
    }

    @Override
    public Position getPlayerHome(String playerName) {
        return httpHelper.get(serverUrl + "/home/" + playerName, Position.class);
    }

    @Override
    public void setPlayerHome(String playerName, Position playerHome) {
        httpHelper.post(serverUrl + "/home/" + playerName + String.format("?x=%d&y=%d&z=%d", playerHome.x, playerHome.y, playerHome.z));
    }
}
