package com.lambdanum.raids.raid.controller.objective;

import com.lambdanum.raids.model.Position;

import java.util.Arrays;
import java.util.List;

public class RaidObjectiveAbstractFactory {

    public ObjectivePoller create(ObjectiveSubscriber subscriber, String command) {
        List<String> args = Arrays.asList(command.split(" "));
        String objectiveType = args.get(0);
        switch (objectiveType) {
            case "ask-item":
                return new AskItemAtPositionObjectivePoller(subscriber, args.get(1), new Position(Integer.parseInt(args.get(2)), Integer.parseInt(args.get(3)), Integer.parseInt(args.get(4))));
            case "bring-player":
                return new BringPlayerObjectivePoller(subscriber, new Position(Integer.parseInt(args.get(1)), Integer.parseInt(args.get(2)), Integer.parseInt(args.get(3))));
        }
        throw new UnknownObjectiveTypeException(objectiveType);
    }
}
