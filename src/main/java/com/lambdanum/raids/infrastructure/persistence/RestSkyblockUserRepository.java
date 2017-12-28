package com.lambdanum.raids.infrastructure.persistence;

import com.lambdanum.raids.HttpHelper;
import com.lambdanum.raids.context.config.ServerProperties;
import com.lambdanum.raids.model.User;
import com.lambdanum.raids.skyblock.SkyblockUserRepository;

public class RestSkyblockUserRepository implements SkyblockUserRepository {

    private HttpHelper httpHelper;
    private String serverUrl;

    public RestSkyblockUserRepository(HttpHelper httpHelper, ServerProperties serverProperties) {
        this.httpHelper = httpHelper;
        this.serverUrl = serverProperties.serverUrl;
    }

    @Override
    public boolean hasUserAlreadyGeneratedIsland(String playerName) {
        User user = httpHelper.get(serverUrl + "/user/" + playerName, User.class);
        return user.generatedIsland;
    }

    @Override
    public void setUserHasAlreadyGeneratedIsland(String playerName) {
        httpHelper.post(serverUrl + "/user/" + playerName + "/isGeneratedIsland");
    }
}
