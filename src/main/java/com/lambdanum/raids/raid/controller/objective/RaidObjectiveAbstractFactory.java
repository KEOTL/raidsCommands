package com.lambdanum.raids.raid.controller.objective;

import com.lambdanum.raids.model.Position;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RaidObjectiveAbstractFactory {

    public ObjectivePoller create(ObjectiveSubscriber subscriber, String command) {
        List<String> args = Arrays.asList(command.split(" "));
        String objectiveType = args.get(0);
        switch (objectiveType) {
            case "ask-item":
                return new AskItemAtPositionObjectivePoller(subscriber, args.get(1), new Position(Integer.parseInt(args.get(2)), Integer.parseInt(args.get(3)), Integer.parseInt(args.get(4))));
            case "bring-player":
                return new BringPlayerObjectivePoller(subscriber, new Position(Integer.parseInt(args.get(1)), Integer.parseInt(args.get(2)), Integer.parseInt(args.get(3))));
            case "timer":
                String delay = args.get(1);
                ZonedDateTime stopTime = ZonedDateTime.now().plusSeconds(Integer.parseInt(delay));
                return new TimerObjectivePoller(subscriber, stopTime);
            case "random-timer":
                String lowerDelayBound = args.get(1);
                String higherDelayBound = args.get(2);
                int waitTime = getRandomIntegerBetween(Integer.parseInt(lowerDelayBound), Integer.parseInt(higherDelayBound));
                ZonedDateTime randomStopTime = ZonedDateTime.now().plusSeconds(waitTime);
                return new TimerObjectivePoller(subscriber, randomStopTime);
        }
        throw new UnknownObjectiveTypeException(objectiveType);
    }

    private int getRandomIntegerBetween(int min, int max) {
        Random random = new Random();
        return min + random.nextInt(max - min);
    }
}
