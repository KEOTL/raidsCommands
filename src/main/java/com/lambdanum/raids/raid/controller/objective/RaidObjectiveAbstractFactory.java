package com.lambdanum.raids.raid.controller.objective;

import com.lambdanum.raids.model.Position;

import java.util.List;

public class RaidObjectiveAbstractFactory {

    public ObjectivePoller create(ObjectiveSubscriber subscriber, String objectiveType, List<String> args) {
        switch (objectiveType) {
            case "ask-item":
                return new AskItemAtPositionObjectivePoller(subscriber, args.get(0), new Position(Integer.parseInt(args.get(1)), Integer.parseInt(args.get(2)), Integer.parseInt(args.get(3))));
        }
        throw new UnknownObjectiveTypeException(objectiveType);
    }
}
