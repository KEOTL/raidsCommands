package com.lambdanum.raids.application;

import com.lambdanum.raids.raid.controller.party.Party;
import com.lambdanum.raids.raid.controller.party.RaidPartyRepository;

import net.minecraft.entity.player.EntityPlayer;

public class RaidPartyService {

    private RaidPartyRepository partyRepository;
    private OnlinePlayerService onlinePlayerService;
    private PlayerHomeService homeService;

    public RaidPartyService(RaidPartyRepository partyRepository, OnlinePlayerService onlinePlayerService, PlayerHomeService homeService) {
        this.partyRepository = partyRepository;
        this.onlinePlayerService = onlinePlayerService;
        this.homeService = homeService;
    }

    public boolean isPlayerInAParty(String playerName) {
        return partyRepository.isPlayerInAParty(playerName);
    }

    public void removePlayerFromTheirParty(String playerName) {
        Party party = partyRepository.getPlayerParty(playerName);
        party.removePlayer(playerName);
        EntityPlayer player = onlinePlayerService.getPlayerByUsername(playerName);
        homeService.asyncTeleportPlayerToHome(player);
    }


    public void createParty(EntityPlayer player) {
        partyRepository.createPartyWithPlayer(player);
        //TODO print player party scoreboard
    }
}
