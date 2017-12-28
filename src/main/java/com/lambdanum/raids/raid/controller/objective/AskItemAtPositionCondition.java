package com.lambdanum.raids.raid.controller.objective;

import com.lambdanum.raids.model.Position;

import net.minecraft.command.ICommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class AskItemAtPositionCondition extends ConditionEventListener {

    private String askedItem;
    private Position position;
    private ICommandManager commandManager;

    public AskItemAtPositionCondition(ConditionObserver subscriber, String askedItem, Position position, ICommandManager commandManager) {
        super(subscriber);
        this.commandManager = commandManager;
    }

    @Override
    protected boolean isConditionMet() {
        MinecraftServer server = null;
        WorldServer world = server.worlds[0];

        return false;
    }
}
