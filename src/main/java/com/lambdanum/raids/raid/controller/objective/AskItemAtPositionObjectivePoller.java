package com.lambdanum.raids.raid.controller.objective;

import com.lambdanum.raids.infrastructure.injection.ComponentLocator;
import com.lambdanum.raids.model.Position;

import net.minecraft.server.MinecraftServer;

public class AskItemAtPositionObjectivePoller extends ObjectivePoller {

    private MinecraftServer minecraftServer = ComponentLocator.INSTANCE.get(MinecraftServer.class);

    private String askedItem;
    private Position position;

    public AskItemAtPositionObjectivePoller(ObjectiveSubscriber subscriber, String askedItem, Position position) {
        super(subscriber);
        this.askedItem = askedItem;
        this.position = position;
    }

    @Override
    protected boolean isConditionMet() {
        String command = String.format("testfor @e[type=Item,x=%s,y=%s,z=%s,dx=0,dy=0,dz=0] {Item:{id:\"%s\"}}", position.x, position.y, position.z, askedItem);
        return !(minecraftServer.commandManager.executeCommand(commandSender, command) == 0);
    }

    @Override
    protected void afterConditionIsMet() {
        String deleteItemCommand = String.format("kill @e[type=Item,x=%s,y=%s,z=%s,dx=0,dy=0,dz=0]", position.x, position.y, position.z);
        minecraftServer.commandManager.executeCommand(commandSender, deleteItemCommand);
    }

    @Override
    public String getObjectiveName() {
        return "ask-item";
    }

    public String getAskedItem() {
        return askedItem;
    }

    public Position getPosition() {
        return position;
    }
}
