package com.lambdanum.raids.infrastructure.persistence;

import com.lambdanum.raids.HttpHelper;
import com.lambdanum.raids.context.config.ServerProperties;
import com.lambdanum.raids.model.Raid;
import com.lambdanum.raids.raid.controller.RaidRepository;

public class RestRaidRepository implements RaidRepository {

    private HttpHelper httpHelper;
    private String serverUrl;

    public RestRaidRepository(HttpHelper httpHelper, ServerProperties serverProperties) {
        this.httpHelper = httpHelper;
        this.serverUrl = serverProperties.serverUrl;
    }

    @Override
    public Raid find(String raidName) {
        try {
            return httpHelper.get(serverUrl + "/raid/" + raidName, Raid.class);
        } catch (Exception e) {
            throw new UnknownRaidException();
        }
    }
}
