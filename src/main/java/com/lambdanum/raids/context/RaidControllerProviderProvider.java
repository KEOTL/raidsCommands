package com.lambdanum.raids.context;

import com.lambdanum.raids.infrastructure.injection.ComponentLocator;
import com.lambdanum.raids.infrastructure.injection.RuntimeProvider;
import com.lambdanum.raids.raid.controller.RaidControllerProvider;
import com.lambdanum.raids.raid.controller.RaidRepository;

public class RaidControllerProviderProvider implements RuntimeProvider<RaidControllerProvider> {

    private RaidControllerProvider raidControllerProvider;

    @Override
    public RaidControllerProvider get() {
        if (raidControllerProvider == null) {
            raidControllerProvider = new RaidControllerProvider(ComponentLocator.INSTANCE.get(RaidRepository.class));
        }
        return raidControllerProvider;
    }
}
