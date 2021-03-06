package com.lambdanum.raids.raid.controller.party;

import net.minecraft.entity.player.EntityPlayer;

public interface RaidPartyRepository {

    boolean isPlayerInAParty(String playerName);

    Party getPlayerParty(String playerName);

    Party createPartyWithPlayer(EntityPlayer player);

    void deleteParty(Party party);

    boolean contains(Party party);
}
