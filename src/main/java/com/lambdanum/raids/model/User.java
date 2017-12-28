package com.lambdanum.raids.model;

public class User {

    public String username;
    public boolean generatedIsland;

    public User() {
    }

    public User(String username, boolean generatedIsland) {
        this.username = username;
        this.generatedIsland = generatedIsland;
    }
}
