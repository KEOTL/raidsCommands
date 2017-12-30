package com.lambdanum.raids.infrastructure.persistence;

import com.lambdanum.raids.raid.controller.party.Party;
import com.lambdanum.raids.raid.controller.party.RaidPartyRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import net.minecraft.entity.player.EntityPlayer;

public class InMemoryRaidPartyRepository implements RaidPartyRepository {

    List<Party> teams = new LinkedList<>();

    @Override
    public boolean isPlayerInAParty(String playerName) {
        return teams.stream().anyMatch(party -> party.containsPlayer(playerName));
    }

    @Override
    public Party getPlayerParty(String playerName) {
        Optional<Party> foundParty = teams.stream().filter(party -> party.containsPlayer(playerName)).findFirst();
        if (foundParty.isPresent()) {
            return foundParty.get();
        }
        throw new PlayerNotInsideAPartyException();
    }

    @Override
    public Party createPartyWithPlayer(EntityPlayer player) {
        Party party = new Party(new ArrayList<>(Arrays.asList(player)));
        teams.add(party);
        return party;
    }

    @Override
    public void deleteParty(String playerName) {
        try {
            Party party = getPlayerParty(playerName);
            teams.remove(party);
        } catch (PlayerNotInsideAPartyException e) {
            // Ignored
        }
    }
}
