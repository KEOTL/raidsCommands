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
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
