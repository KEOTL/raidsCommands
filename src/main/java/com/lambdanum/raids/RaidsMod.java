package com.lambdanum.raids;

import com.lambdanum.raids.commands.BeginCommand;
import com.lambdanum.raids.commands.DailyCommand;
import com.lambdanum.raids.commands.HomeCommand;
import com.lambdanum.raids.commands.LootCommand;
import com.lambdanum.raids.commands.SetHomeCommand;
import com.lambdanum.raids.commands.VisitCommand;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = "raids", version = "0.0.3", acceptableRemoteVersions = "*")
@Mod.EventBusSubscriber
public class RaidsMod {

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new LootCommand());
        event.registerServerCommand(new BeginCommand());
        event.registerServerCommand(new HomeCommand());
        event.registerServerCommand(new DailyCommand());
        event.registerServerCommand(new VisitCommand());
        event.registerServerCommand(new SetHomeCommand());
    }
}
