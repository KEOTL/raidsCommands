package com.lambdanum.raids.raid.controller.objective;

public abstract class ConditionEventListener implements Runnable {

    private ConditionObserver subscriber;

    public ConditionEventListener(ConditionObserver subscriber) {
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
