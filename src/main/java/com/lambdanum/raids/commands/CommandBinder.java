package com.lambdanum.raids.commands;

import com.lambdanum.raids.infrastructure.ComponentLocator;

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
