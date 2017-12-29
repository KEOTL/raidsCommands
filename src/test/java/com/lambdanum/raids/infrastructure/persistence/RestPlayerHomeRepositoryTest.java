package com.lambdanum.raids.infrastructure.persistence;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lambdanum.raids.HttpHelper;
import com.lambdanum.raids.context.config.ServerProperties;
import com.lambdanum.raids.model.Position;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RestPlayerHomeRepositoryTest {

    @Mock
    private HttpHelper httpHelper;

    private RestPlayerHomeRepository homeRepository;

    private static final String PLAYER = "player";
    private static final String SERVER_URL = "https://foo";
    private static final Position PLAYER_HOME = new Position(0,100,0);

    @Before
    public void initializeRepository() {
        ServerProperties properties = new ServerProperties();
        properties.serverUrl = SERVER_URL;
        homeRepository = new RestPlayerHomeRepository(httpHelper, properties);
    }

    @Test
    public void whenGettingPlayerHome_thenCallsServerWithProperSyntax() throws Exception {
        when(httpHelper.get(SERVER_URL + "/home/" + PLAYER, Position.class)).thenReturn(PLAYER_HOME);

        Position playerHome = homeRepository.getPlayerHome(PLAYER);

        assertEquals(PLAYER_HOME, playerHome);
    }

    @Test
    public void whenSettingPlayerHome_thenCallsServerWithProperSyntax() {
        homeRepository.setPlayerHome(PLAYER, PLAYER_HOME);

        verify(httpHelper).post(SERVER_URL + String.format("/home/" + PLAYER + "?x=%d&y=%d&z=%d", PLAYER_HOME.x, PLAYER_HOME.y, PLAYER_HOME.z));
    }
}
