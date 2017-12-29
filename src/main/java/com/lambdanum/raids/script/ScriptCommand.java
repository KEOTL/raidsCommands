package com.lambdanum.raids.script;

import java.util.List;

public class ScriptCommand {

    public final String conditionClause;
    public final List<String> ifStatements;
    public final List<String> elseStatements;

    public ScriptCommand(String conditionClause, List<String> ifStatements, List<String> elseStatements) {
        this.conditionClause = conditionClause;
        this.ifStatements = ifStatements;
        this.elseStatements = elseStatements;
    }
}
