package com.lambdanum.raids.application;

import com.lambdanum.raids.model.Position;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerTeleportService {

    public void teleportPlayer(EntityPlayer player, Position position) {
        player.attemptTeleport(position.x, position.y, position.z);
    }
}
