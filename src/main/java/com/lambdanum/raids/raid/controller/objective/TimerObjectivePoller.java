package com.lambdanum.raids.raid.controller.objective;

import java.time.ZonedDateTime;

public class TimerObjectivePoller extends ObjectivePoller {

    private ZonedDateTime stopTime;

    public TimerObjectivePoller(ObjectiveSubscriber subscriber, ZonedDateTime stopTime) {
        super(subscriber);
        this.stopTime = stopTime;
    }

    @Override
    protected boolean isConditionMet() {
        return ZonedDateTime.now().isAfter(stopTime);
    }

    @Override
    protected void afterConditionIsMet() {
    }

    @Override
    public String getObjectiveName() {
        return "timer";
    }
}
