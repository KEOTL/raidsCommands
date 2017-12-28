package com.lambdanum.raids.context;

import com.lambdanum.raids.commands.BeginCommand;
import com.lambdanum.raids.commands.DailyCommand;
import com.lambdanum.raids.commands.EchoCommand;
import com.lambdanum.raids.commands.HomeCommand;
import com.lambdanum.raids.commands.LootCommand;
import com.lambdanum.raids.commands.SetHomeCommand;
import com.lambdanum.raids.commands.StartRaidCommand;
import com.lambdanum.raids.commands.VisitCommand;
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
    }
}
