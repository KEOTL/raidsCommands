package com.lambdanum.raids.raid.controller.objective;

import com.lambdanum.raids.infrastructure.injection.ComponentLocator;
import com.lambdanum.raids.model.Position;

import net.minecraft.server.MinecraftServer;

public class BringPlayerObjectivePoller extends ObjectivePoller {

    private final MinecraftServer minecraftServer = ComponentLocator.INSTANCE.get(MinecraftServer.class);

    private Position position;

    public BringPlayerObjectivePoller(ObjectiveSubscriber subscriber, Position position) {
        super(subscriber);
        this.position = position;
    }

    @Override
    protected boolean isConditionMet() {
        String command = String.format("testfor @a[x=%s,y=%s,z=%s,dx=0,dy=0,dz=0]", position.x, position.y, position.z);
        return !(minecraftServer.commandManager.executeCommand(commandSender, command) == 0);
    }

    @Override
    protected void afterConditionIsMet() {
    }

    @Override
    public String getObjectiveName() {
        return "bring-player";
    }
}
