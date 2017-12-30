package com.lambdanum.raids.raid.controller.party.loot;

import com.lambdanum.raids.model.Raid;
import com.lambdanum.raids.raid.controller.party.Party;

public interface TeamLootStrategy {

    void distributeLoot(Party party, Raid raid);
}
