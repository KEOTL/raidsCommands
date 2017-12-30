package com.lambdanum.raids.raid.controller.party;

import com.lambdanum.raids.application.OnlinePlayerService;
import com.lambdanum.raids.application.PlayerTeleportService;
import com.lambdanum.raids.infrastructure.injection.ComponentLocator;
import com.lambdanum.raids.model.LootItem;
import com.lambdanum.raids.model.Position;
import com.lambdanum.raids.model.Raid;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.GameType;

public class Party {

    public static final Party EMPTY_PARTY = new Party(Collections.emptyList());

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

    public boolean hasAppropriatePlayerCount(Raid raid) {
        long playerCount = players.stream().filter(player -> onlinePlayerService.isPlayerOnline(player.getName())).count();
        return playerCount >= raid.minPlayers && playerCount <= raid.maxPlayers;
    }

    public boolean arePlayersCarryingOnlyAllowedItemsForRaid(Raid raid) {
        List<String> allowedItems = raid.allowedItems;
        for (EntityPlayer player : players) {
            if (isPlayerCarryingDisallowedItems(player, allowedItems)) {
                return false;
            }
        }
        return true;
    }

    private boolean isPlayerCarryingDisallowedItems(EntityPlayer player, List<String> allowedItems) {
        return inventoryContainsDisallowedItems(player.inventory.mainInventory, allowedItems)
            || inventoryContainsDisallowedItems(player.inventory.armorInventory, allowedItems)
            || inventoryContainsDisallowedItems(player.inventory.offHandInventory, allowedItems);
    }

    private boolean inventoryContainsDisallowedItems(List<ItemStack> inventory, List<String> allowedItems) {
        return inventory.stream().map(ItemStack::getItem).map(Item::getRegistryName)
            .map(ResourceLocation::toString).anyMatch(carriedItem -> !allowedItems.contains(carriedItem) && !"minecraft:air".equals(carriedItem));
    }
}
