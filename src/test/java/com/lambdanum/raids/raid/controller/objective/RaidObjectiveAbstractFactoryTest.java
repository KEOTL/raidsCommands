package com.lambdanum.raids.raid.controller.objective;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import com.lambdanum.raids.infrastructure.injection.ComponentLocator;
import com.lambdanum.raids.model.Position;

import net.minecraft.server.MinecraftServer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RaidObjectiveAbstractFactoryTest {

    private RaidObjectiveAbstractFactory objectiveAbstractFactory = new RaidObjectiveAbstractFactory();

    @Mock
    private ObjectiveSubscriber subscriber;

    @Mock
    private MinecraftServer minecraftServer;

    private TestableComponentLocator componentLocator;

    @Before
    public void mockMinecraftServer() {
        componentLocator = new TestableComponentLocator();
        ComponentLocator.INSTANCE = componentLocator;
        componentLocator.bind(minecraftServer).to(MinecraftServer.class);
    }

    @Test
    public void whenGettingAskItemObjective_thenAskItemIsProperlyCreated() {
        String command = "ask-item minecraft:chest 0 62 0";

        ObjectivePoller objectivePoller = objectiveAbstractFactory.create(subscriber, command);

        assertTrue(objectivePoller instanceof AskItemAtPositionObjectivePoller);
        AskItemAtPositionObjectivePoller askItem = (AskItemAtPositionObjectivePoller) objectivePoller;
        assertEquals(new Position(0,62,0), askItem.getPosition());
        assertEquals("minecraft:chest", askItem.getAskedItem());
    }

    @Test
    public void whenGettingBringPlayerObjective_thenBringPlayerIsProperlyCreated() {
        String command = "bring-player 0 62 0";

        ObjectivePoller objectivePoller = objectiveAbstractFactory.create(subscriber, command);

        assertTrue(objectivePoller instanceof BringPlayerObjectivePoller);
    }

}
