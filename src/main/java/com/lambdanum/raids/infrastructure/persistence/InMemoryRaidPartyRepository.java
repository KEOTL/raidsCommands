package com.lambdanum.raids.infrastructure.persistence;

import com.lambdanum.raids.infrastructure.injection.ComponentLocator;
import com.lambdanum.raids.minecraft.OnlinePlayerRepository;
import com.lambdanum.raids.raid.controller.party.Party;
import com.lambdanum.raids.raid.controller.party.RaidPartyRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import net.minecraft.entity.player.EntityPlayer;

public class InMemoryRaidPartyRepository implements RaidPartyRepository {

    private List<Party> teams = new LinkedList<>();
    private InMemoryRaidRepositoryGarbageCollector garbageCollector;

    public InMemoryRaidPartyRepository() {
        garbageCollector = new InMemoryRaidRepositoryGarbageCollector(this);
        new Thread(garbageCollector).start();
    }

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
        Party party = new Party(new ArrayList<>(Collections.singletonList(player)));
        teams.add(party);
        return party;
    }

    @Override
    public void deleteParty(Party party) {
        teams.remove(party);
    }

    @Override
    public boolean contains(Party party) {
        return teams.contains(party);
    }

    public synchronized void deleteEmptyParties() {
        try {
            for (Party party : teams) {
                if (party.isEmpty()) {
                    teams.remove(party);
                }
            }
        } catch (Exception e) {
            return;
        }
    }
    public void refreshParties() {
        OnlinePlayerRepository onlinePlayerRepository = ComponentLocator.INSTANCE.get(OnlinePlayerRepository.class);
        for (Party party : teams) {
            party.refreshPlayers(onlinePlayerRepository);
        }
    }
}
