package com.lambdanum.raids.application;

import com.lambdanum.raids.raid.controller.party.Party;
import com.lambdanum.raids.raid.controller.party.RaidPartyRepository;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

public class RaidPartyService {

    private RaidPartyRepository partyRepository;
    private OnlinePlayerService onlinePlayerService;
    private RaidService raidService;
    private RaidExitService raidExitService;

    public RaidPartyService(RaidPartyRepository partyRepository, OnlinePlayerService onlinePlayerService, RaidService raidService, RaidExitService raidExitService) {
        this.partyRepository = partyRepository;
        this.onlinePlayerService = onlinePlayerService;
        this.raidService = raidService;
        this.raidExitService = raidExitService;
    }

    public boolean isPlayerInAParty(String playerName) {
        return partyRepository.isPlayerInAParty(playerName);
    }

    public void removePlayerFromTheirParty(String playerName) {
        Party party = partyRepository.getPlayerParty(playerName);
        party.removePlayer(playerName);
        EntityPlayer player = onlinePlayerService.getPlayerByUsername(playerName);
        if (raidService.isInARaid(player)) {
            raidExitService.sendPlayerHome(player);
        }
    }


    public void createParty(EntityPlayer player) {
        partyRepository.createPartyWithPlayer(player);
        //TODO print player party scoreboard
    }

    public List<String> getPartyMembers(String playerName) {
        Party playerParty = partyRepository.getPlayerParty(playerName);
        return playerParty.getPlayers();
    }

    public void inviteToParty(String hostPlayer, String invitedPlayer) {
        Party hostParty = partyRepository.getPlayerParty(hostPlayer);
        EntityPlayer invitee = onlinePlayerService.getPlayerByUsername(invitedPlayer);
        hostParty.addPlayer(invitee);
    }
}
