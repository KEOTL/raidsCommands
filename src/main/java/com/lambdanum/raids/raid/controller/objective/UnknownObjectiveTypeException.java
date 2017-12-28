package com.lambdanum.raids.raid.controller.objective;

public class UnknownObjectiveTypeException extends RuntimeException {
    public UnknownObjectiveTypeException(String objectiveType) {
        super(objectiveType);
    }
}
