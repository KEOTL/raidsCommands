package com.lambdanum.raids.raid.controller.objective;

import com.lambdanum.raids.model.Position;

public class RaidObjectiveAbstractFactory {

    public ObjectivePoller create(ObjectiveSubscriber subscriber, String objectiveType, String... args) {
        switch (objectiveType) {
            case "ask-item":
                return new AskItemAtPositionObjectivePoller(subscriber, args[0], new Position(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3])));
        }
        throw new UnknownObjectiveTypeException(objectiveType);
    }
}
