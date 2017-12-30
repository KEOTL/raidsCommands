package com.lambdanum.raids.context;

import com.lambdanum.raids.commands.BeginCommand;
import com.lambdanum.raids.commands.DailyCommand;
import com.lambdanum.raids.commands.EchoCommand;
import com.lambdanum.raids.commands.HomeCommand;
import com.lambdanum.raids.commands.LootCommand;
import com.lambdanum.raids.commands.SetHomeCommand;
import com.lambdanum.raids.commands.VisitCommand;
import com.lambdanum.raids.commands.raids.DefeatCommand;
import com.lambdanum.raids.commands.raids.ExitRaidCommand;
import com.lambdanum.raids.commands.raids.StartRaidCommand;
import com.lambdanum.raids.commands.raids.VictoryCommand;
import com.lambdanum.raids.commands.raids.party.PartyCreateCommand;
import com.lambdanum.raids.commands.raids.party.PartyInfoCommand;
import com.lambdanum.raids.commands.raids.party.PartyInviteCommand;
import com.lambdanum.raids.commands.raids.party.PartyLeaveCommand;
import com.lambdanum.raids.infrastructure.injection.AbstractBinder;

public class CommandBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(BeginCommand.class).to(BeginCommand.class);
        bind(DailyCommand.class).to(DailyCommand.class);
        bind(HomeCommand.class).to(HomeCommand.class);
        bind(LootCommand.class).to(LootCommand.class);
        bind(SetHomeCommand.class).to(SetHomeCommand.class);
        bind(EchoCommand.class).to(EchoCommand.class);
        bind(VisitCommand.class).to(VisitCommand.class);
        bind(StartRaidCommand.class).to(StartRaidCommand.class);
        bind(VictoryCommand.class).to(VictoryCommand.class);
        bind(DefeatCommand.class).to(DefeatCommand.class);
        bind(ExitRaidCommand.class).to(ExitRaidCommand.class);
        bind(PartyCreateCommand.class).to(PartyCreateCommand.class);
        bind(PartyInfoCommand.class).to(PartyInfoCommand.class);
        bind(PartyInviteCommand.class).to(PartyInviteCommand.class);
        bind(PartyLeaveCommand.class).to(PartyLeaveCommand.class);
    }
}
