package com.lambdanum.raids.raid.controller.party;

import com.lambdanum.raids.application.OnlinePlayerService;
import com.lambdanum.raids.application.PlayerTeleportService;
import com.lambdanum.raids.infrastructure.injection.ComponentLocator;
import com.lambdanum.raids.model.LootItem;
import com.lambdanum.raids.model.Position;
import com.lambdanum.raids.model.Raid;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.GameType;

public class Party {

    private final OnlinePlayerService onlinePlayerService = ComponentLocator.INSTANCE.get(OnlinePlayerService.class);

    private List<EntityPlayer> players;

    public Party(List<EntityPlayer> players) {
        this.players = players;
    }

    public synchronized void addPlayer(EntityPlayer player) {
        if (!players.contains(player)) {
            players.add(player);
            broadcastToMembers(player.getName() + " has been added to the party.");
        }
    }

    public void broadcastToMembers(String message) {
        for (EntityPlayer player : players) {
            player.sendMessage(new TextComponentString("[Party] "+ message));
        }
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

    public List<String> getPlayers() {
        return players.stream().filter(Objects::nonNull).map(EntityPlayer::getName).collect(Collectors.toList());
    }

    public void giveToPlayer(String playerName, LootItem lootItem) {
        Optional<EntityPlayer> player = players.stream().filter(p -> playerName.equals(p.getName())).findFirst();
        if (player.isPresent()) {
            player.get().addItemStackToInventory(new ItemStack(Item.getByNameOrId(lootItem.minecraftId), lootItem.amount));
        }
    }

    public boolean containsPlayer(String playerName) {
        return players.stream().map(EntityPlayer::getName).anyMatch(playerName::equals);
    }

    public void removePlayer(String playerName) {
        players.removeIf(player -> player.getName().equals(playerName));
        broadcastToMembers(playerName + " has left the party.");
    }

    public boolean isEmpty() {
        return players.stream().filter(Objects::nonNull).filter(player -> onlinePlayerService.isPlayerOnline(player.getName())).collect(Collectors.toList()).isEmpty();
    }

    public boolean canEnterRaid(Raid raid) {
        long playerCount = players.stream().filter(player -> onlinePlayerService.isPlayerOnline(player.getName())).count();
        return playerCount >= raid.minPlayers && playerCount <= raid.maxPlayers;
    }
}
