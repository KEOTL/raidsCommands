package com.lambdanum.raids.context;

import com.lambdanum.raids.commands.BeginCommand;
import com.lambdanum.raids.commands.DailyCommand;
import com.lambdanum.raids.commands.EchoCommand;
import com.lambdanum.raids.commands.HomeCommand;
import com.lambdanum.raids.commands.LootCommand;
import com.lambdanum.raids.commands.SetHomeCommand;
import com.lambdanum.raids.commands.raids.DefeatCommand;
import com.lambdanum.raids.commands.raids.ExitRaidCommand;
import com.lambdanum.raids.commands.raids.StartRaidCommand;
import com.lambdanum.raids.commands.VisitCommand;
import com.lambdanum.raids.commands.raids.VictoryCommand;
import com.lambdanum.raids.commands.raids.party.CreatePartyCommand;
import com.lambdanum.raids.commands.raids.party.LeavePartyCommand;
import com.lambdanum.raids.infrastructure.injection.ComponentLocator;

public class CommandBinder extends ComponentLocator {

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
        bind(CreatePartyCommand.class).to(CreatePartyCommand.class);
        bind(LeavePartyCommand.class).to(LeavePartyCommand.class);
    }
}
