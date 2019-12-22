package com.lambdanum.raids.infrastructure.persistence;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lambdanum.raids.HttpHelper;
import com.lambdanum.raids.context.config.ServerProperties;
import com.lambdanum.raids.model.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RestSkyblockUserRepositoryTest {

    @Mock
    private HttpHelper httpHelper;

    private RestSkyblockUserRepository skyblockUserRepository;
    private User user;

    private static final String SERVER_URL = "https://foo";
    private static final String PLAYER_NAME = "player";

    @Before
    public void initialize() {
        ServerProperties serverProperties = new ServerProperties(SERVER_URL);
        skyblockUserRepository = new RestSkyblockUserRepository(httpHelper, serverProperties);
        user = new User();
        user.generatedIsland = true;
    }

    @Test
    public void whenCheckingHasUserAlreadyGeneratedIsland_thenCallsServerWithProperSyntax() {
        when(httpHelper.get(SERVER_URL + "/user/" + PLAYER_NAME, User.class)).thenReturn(user);

        boolean userHasAlreadyGeneratedIsland = skyblockUserRepository.hasUserAlreadyGeneratedIsland(PLAYER_NAME);

        assertTrue(userHasAlreadyGeneratedIsland);
    }

    @Test
    public void whenSettingUserHasGeneratedIslandOnce_thenPostsToTheServer() {
        skyblockUserRepository.setUserHasAlreadyGeneratedIsland(PLAYER_NAME);

        verify(httpHelper).post(SERVER_URL + "/user/" + PLAYER_NAME + "/isGeneratedIsland");
    }

}
