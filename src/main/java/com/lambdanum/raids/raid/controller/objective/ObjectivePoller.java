package com.lambdanum.raids.raid.controller.objective;

public abstract class ObjectivePoller implements Runnable {

    private ObjectiveSubscriber subscriber;

    public ObjectivePoller(ObjectiveSubscriber subscriber) {
        this.subscriber = subscriber;
    }

    protected abstract boolean isConditionMet();

    @Override
    public void run() {
        while (!isConditionMet()) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                // Ignored
            }
        }
        subscriber.notifyOnWatchedCondition();
    }
}
