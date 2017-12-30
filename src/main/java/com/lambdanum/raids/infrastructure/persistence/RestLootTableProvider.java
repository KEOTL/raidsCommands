package com.lambdanum.raids.infrastructure.persistence;

import com.lambdanum.raids.HttpHelper;
import com.lambdanum.raids.context.config.ServerProperties;
import com.lambdanum.raids.loot.LootTableProvider;
import com.lambdanum.raids.model.LootItem;

import java.util.List;

import scala.actors.threadpool.Arrays;

public class RestLootTableProvider implements LootTableProvider {

    private HttpHelper httpHelper;
    private String serverUrl;

    public RestLootTableProvider(HttpHelper httpHelper, ServerProperties serverProperties) {
        this.httpHelper = httpHelper;
        serverUrl = serverProperties.serverUrl;
    }

    @Override
    public List<LootItem> roll(String tableName, String playerName) {
        LootItem[] items = httpHelper.get(serverUrl + "/distributeLoot/" + tableName + "?username=" + playerName.replace(" ", ""), LootItem[].class);
        return Arrays.asList(items);
    }
}
