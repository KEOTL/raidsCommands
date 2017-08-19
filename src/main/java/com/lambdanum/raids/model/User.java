package com.lambdanum.raids.model;

public class User {

    private String username;
    private boolean generatedIsland;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isGeneratedIsland() {
        return generatedIsland;
    }

    public void setGeneratedIsland(boolean generatedIsland) {
        this.generatedIsland = generatedIsland;
    }
}
