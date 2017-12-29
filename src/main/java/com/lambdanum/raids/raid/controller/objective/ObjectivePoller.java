package com.lambdanum.raids.raid.controller.objective;

import java.util.concurrent.atomic.AtomicBoolean;

import net.minecraft.command.ICommandSender;

public abstract class ObjectivePoller implements Runnable {

    public volatile AtomicBoolean shouldStop = new AtomicBoolean(false);

    private ObjectiveSubscriber subscriber;
    protected ICommandSender commandSender;

    public ObjectivePoller(ObjectiveSubscriber subscriber) {
        this.subscriber = subscriber;
    }

    public void setCommandSender(ICommandSender commandSender) {
        this.commandSender = commandSender;
    }

    protected abstract boolean isConditionMet();

    protected abstract void cleanup();

    public abstract String getObjectiveName();

    @Override
    public void run() {
        while (!isConditionMet() && !shouldStop.get()) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                // Ignored
            }
        }
        if (!shouldStop.get()) {
            subscriber.notifyOnWatchedCondition();
        }
        cleanup();
    }
}
