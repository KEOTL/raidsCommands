package com.lambdanum.raids.infrastructure.persistence;

import java.util.concurrent.atomic.AtomicBoolean;

public class InMemoryRaidRepositoryGarbageCollector implements Runnable {

    private InMemoryRaidPartyRepository inMemoryRaidPartyRepository;

    private AtomicBoolean shouldStop = new AtomicBoolean(false);

    public InMemoryRaidRepositoryGarbageCollector(InMemoryRaidPartyRepository inMemoryRaidPartyRepository) {
        this.inMemoryRaidPartyRepository = inMemoryRaidPartyRepository;
    }

    @Override
    public void run() {
        while (!shouldStop.get()) {
            inMemoryRaidPartyRepository.deleteEmptyParties();
            inMemoryRaidPartyRepository.refreshParties();
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
