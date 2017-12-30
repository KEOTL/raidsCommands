package com.lambdanum.raids.infrastructure.persistence;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.lambdanum.raids.HttpHelper;
import com.lambdanum.raids.context.config.ServerProperties;
import com.lambdanum.raids.model.LootItem;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RestLootTableProviderTest {

    @Mock
    private HttpHelper httpHelper;

    private RestLootTableProvider lootTableProvider;

    private static final String SERVER_URL = "https://foo";
    private static final String PLAYER_NAME = "player";

    @Before
    public void initialize() {
        ServerProperties serverProperties = new ServerProperties();
        serverProperties.serverUrl = SERVER_URL;
        lootTableProvider = new RestLootTableProvider(httpHelper, serverProperties);
    }

    @Test
    public void whenRollingLoot_thenCallsServerWithProperSyntax() {
        LootItem[] expectedItems = new LootItem[] {new LootItem()};
        when(httpHelper.get(SERVER_URL + "/distributeLoot/table?username=" + PLAYER_NAME, LootItem[].class)).thenReturn(expectedItems);

        List<LootItem> table = lootTableProvider.roll("table", PLAYER_NAME);

        for (LootItem expectedItem : expectedItems) {
            assertTrue(table.contains(expectedItem));
        }
        assertEquals("size", expectedItems.length, table.size());
    }
}
