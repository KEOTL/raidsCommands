package com.lambdanum.raids.context;

import com.lambdanum.raids.infrastructure.injection.ComponentLocator;
import com.lambdanum.raids.infrastructure.injection.RuntimeProvider;
import com.lambdanum.raids.raid.controller.RaidControllerRepository;
import com.lambdanum.raids.raid.controller.RaidRepository;

public class RaidControllerProviderSingletonProvider implements RuntimeProvider<RaidControllerRepository> {

    private RaidControllerRepository raidControllerRepository;

    @Override
    public RaidControllerRepository get() {
        if (raidControllerRepository == null) {
            raidControllerRepository = new RaidControllerRepository(ComponentLocator.INSTANCE.get(RaidRepository.class));
        }
        return raidControllerRepository;
    }
}
