package com.lambdanum.raids.script;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NestedCommandParserTest {

    private NestedCommandParser nestedCommandParser = new NestedCommandParser();

    @Test
    public void givenCommandWithCondition_thenReturnScriptCommandWithConditionAndBodyStatements() {
        String command = "if x y z ? do something ;";

        ScriptCommand scriptCommand = nestedCommandParser.parse(command);

        assertEquals("if x y z", scriptCommand.conditionClause);
        assertEquals(1, scriptCommand.ifStatements.size());
        assertEquals("do something", scriptCommand.ifStatements.get(0));
    }

    @Test
    public void givenCommandWithConditionAndTwoStatements_thenReturnCommandWithConditionAndTwoBodyStatements() {
        String command  = "if x,y,z? do stuff x; do stuff y";

        ScriptCommand scriptCommand = nestedCommandParser.parse(command);

        assertEquals("if x,y,z", scriptCommand.conditionClause);
        assertEquals(2, scriptCommand.ifStatements.size());
        assertEquals("do stuff x", scriptCommand.ifStatements.get(0));
        assertEquals("do stuff y", scriptCommand.ifStatements.get(1));
    }

    @Test
    public void givenIfStatementWithTwoBranches_thenReturnCommandWithTwoBranches() {
        String command = "if x?true:false;and something else;";

        ScriptCommand scriptCommand = nestedCommandParser.parse(command);

        assertEquals("if x", scriptCommand.conditionClause);
        assertEquals(1, scriptCommand.ifStatements.size());
        assertEquals("true", scriptCommand.ifStatements.get(0));
        assertEquals(2, scriptCommand.elseStatements.size());
        assertEquals("false", scriptCommand.elseStatements.get(0));
        assertEquals("and something else", scriptCommand.elseStatements.get(1));
    }

    @Test
    public void givenIfStatementWithOnlyAnElseClause_thenCommandHasNoIfStatementsAndOnlyElseStatements() {
        String command = "if x?: else stuff";

        ScriptCommand scriptCommand = nestedCommandParser.parse(command);

        assertEquals("if x", scriptCommand.conditionClause);
        assertEquals(0, scriptCommand.ifStatements.size());
        assertEquals(1, scriptCommand.elseStatements.size());
        assertEquals("else stuff", scriptCommand.elseStatements.get(0));
    }
}
