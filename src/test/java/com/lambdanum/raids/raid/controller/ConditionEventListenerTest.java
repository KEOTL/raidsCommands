package com.lambdanum.raids.raid.controller;

import com.lambdanum.raids.raid.controller.objective.ConditionEventListener;

import org.junit.Before;

public class ConditionEventListenerTest {

    private ConditionEventListener conditionEventListener;

    @Before
    public void initializeWatchingThread() {
        conditionEventListener = new ConditionEventListener();
    }



}
