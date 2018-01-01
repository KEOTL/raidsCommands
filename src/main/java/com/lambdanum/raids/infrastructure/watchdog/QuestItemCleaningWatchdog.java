package com.lambdanum.raids.infrastructure.watchdog;

import com.lambdanum.raids.application.QuestItemCleaningService;

public class QuestItemCleaningWatchdog implements Runnable {

    private QuestItemCleaningService questItemCleaningService;

    public QuestItemCleaningWatchdog(QuestItemCleaningService questItemCleaningService) {
        this.questItemCleaningService = questItemCleaningService;
    }

    private volatile boolean stop = false;

    @Override
    public void run() {
        while (!stop) {
            questItemCleaningService.clearQuestItemsInInventoryOfPlayersInTheOverworld();
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
