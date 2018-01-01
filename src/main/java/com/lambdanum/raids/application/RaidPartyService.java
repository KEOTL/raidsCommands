package com.lambdanum.raids.application;

import com.lambdanum.raids.minecraft.OnlinePlayerRepository;
import com.lambdanum.raids.raid.controller.party.Party;
import com.lambdanum.raids.raid.controller.party.PlayerAlreadyInAPartyException;
import com.lambdanum.raids.raid.controller.party.RaidPartyRepository;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

public class RaidPartyService {

    private RaidPartyRepository partyRepository;
    private OnlinePlayerRepository onlinePlayerRepository;
    private RaidService raidService;
    private RaidExitService raidExitService;

    public RaidPartyService(RaidPartyRepository partyRepository, OnlinePlayerRepository onlinePlayerRepository, RaidService raidService, RaidExitService raidExitService) {
        this.partyRepository = partyRepository;
        this.onlinePlayerRepository = onlinePlayerRepository;
        this.raidService = raidService;
        this.raidExitService = raidExitService;
    }

    public boolean isPlayerInAParty(String playerName) {
        return partyRepository.isPlayerInAParty(playerName);
    }

    public void removePlayerFromTheirParty(String playerName) {
        Party party = partyRepository.getPlayerParty(playerName);
        party.removePlayer(playerName);
        EntityPlayer player = onlinePlayerRepository.getPlayerByUsername(playerName);
        if (raidService.isInARaid(player)) {
            raidExitService.sendPlayerHome(player);
        }
        if (party.isEmpty()) {
            partyRepository.deleteParty(party);
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
        if (isPlayerInAParty(invitedPlayer)) {
            throw new PlayerAlreadyInAPartyException();
        }
        Party hostParty = partyRepository.getPlayerParty(hostPlayer);
        EntityPlayer invitee = onlinePlayerRepository.getPlayerByUsername(invitedPlayer);

        hostParty.addPlayer(invitee);
    }

    public void tellParty(String name, String message) {
        Party party = partyRepository.getPlayerParty(name);
        party.broadcastToMembers(String.format("<%s> %s", name, message));
    }
}
