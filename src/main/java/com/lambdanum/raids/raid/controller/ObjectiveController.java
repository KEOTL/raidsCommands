package com.lambdanum.raids.raid.controller;

import com.lambdanum.raids.raid.controller.objective.ObjectivePoller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.command.ICommandSender;

public class ObjectiveController {

    private volatile Map<ObjectivePoller, Thread> pollersAndTheirThreads = new ConcurrentHashMap<>();
    private ICommandSender commandSender;

    public ObjectiveController(RaidCommandSender commandSender) {
        this.commandSender = commandSender;
    }

    public void addObjectiveAndSetupPolling(ObjectivePoller objectivePoller) {
        objectivePoller.setCommandSender(commandSender);
        Thread pollerThread = new Thread(objectivePoller);
        pollersAndTheirThreads.put(objectivePoller, pollerThread);
        pollerThread.start();
    }

    public synchronized void stopAllPollers() {
        for (ObjectivePoller objective : pollersAndTheirThreads.keySet()) {
            objective.shouldStop.set(true);
        }
        for (Thread pollerThread : pollersAndTheirThreads.values()) {
            try {
                pollerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
