package com.lambdanum.raids.script;

import com.lambdanum.raids.raid.controller.RaidController;
import com.lambdanum.raids.raid.controller.objective.ObjectiveSubscriber;

import java.util.List;

public class ServerCommandObjectiveSubscriber implements ObjectiveSubscriber {

    private final List<String> statements;
    private RaidController raidController;

    public ServerCommandObjectiveSubscriber(List<String> statements, RaidController raidController) {
        this.statements = statements;
        this.raidController = raidController;
    }

    @Override
    public void notifyOnWatchedCondition() {
        for (String statement : statements) {
            raidController.executeCommandInRaid(statement);
        }
    }
}
