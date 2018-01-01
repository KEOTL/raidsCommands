package com.lambdanum.raids;

import com.lambdanum.raids.application.QuestItemCleaningService;
import com.lambdanum.raids.infrastructure.injection.ComponentLocator;
import com.lambdanum.raids.infrastructure.watchdog.QuestItemCleaningWatchdog;

import net.minecraft.command.ICommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = "raids", version = "0.0.4", acceptableRemoteVersions = "*")
@Mod.EventBusSubscriber
public class RaidsMod {

    private ComponentLocator locator = ComponentLocator.INSTANCE;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        for (ICommand command :locator.getAllChildren(ICommand.class)) {
            event.registerServerCommand(command);
        }
        new Thread(new QuestItemCleaningWatchdog(locator.get(QuestItemCleaningService.class))).start();
    }
}
