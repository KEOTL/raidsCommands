package com.lambdanum.raids.script;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class NestedCommandParser {

    public static final String CONDITION_SEPARATOR = "[?]";
    public static final String STATEMENT_SEPARATOR = ";";
    public static final String BRANCH_SEPARATOR = ":";

    public ScriptCommand parse(String command) {
        String[] split = command.split(CONDITION_SEPARATOR);
        String condition = split[0];
        List<String> branches = Arrays.asList(split[1].split(BRANCH_SEPARATOR));

        List<String> ifStatements = Collections.emptyList();
        if (branches.size() >= 1) {
            ifStatements = Arrays.asList(branches.get(0).split(STATEMENT_SEPARATOR));
        }
        List<String> elseStatements = Collections.emptyList();
        if (branches.size() >= 2) {
            elseStatements = Arrays.asList(branches.get(1).split(STATEMENT_SEPARATOR));
        }
        return new ScriptCommand(trim(condition), trim(ifStatements), trim(elseStatements));
    }

    private String trim(String input) {
        return input.trim();
    }

    private List<String> trim(List<String> input) {
        return input.stream().filter(StringUtils::isNotBlank).map(String::trim).collect(Collectors.toList());
    }
}
