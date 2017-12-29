package com.lambdanum.raids.raid.controller.objective;

import com.lambdanum.raids.infrastructure.injection.ComponentLocator;

public class TestableComponentLocator extends ComponentLocator {

    @Override
    public innerIntermediate bind(Object type) {
        return super.bind(type);
    }
}
