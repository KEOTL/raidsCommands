package com.lambdanum.raids.raid.controller.party;

import com.lambdanum.raids.application.PlayerTeleportService;
import com.lambdanum.raids.model.Position;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.GameType;

public class Party {

    private List<EntityPlayer> players;

    public Party(List<EntityPlayer> players) {
        this.players = players;
    }

    public synchronized void addPlayer(EntityPlayer player) {
        players.add(player);
    }

    public boolean areAllMembersInDimension(int dimension) {
        return players.stream().allMatch(player -> player.dimension == dimension);
    }

    public void teleportAllPlayers(PlayerTeleportService teleportService, int dimension, Position position) {
        for (EntityPlayer player : players) {
            teleportService.teleportPlayerToDimension(player, dimension, position);
        }
    }

    public void setGameType(GameType gameType) {
        for (EntityPlayer player : players) {
            player.setGameType(gameType);
        }
    }
}
