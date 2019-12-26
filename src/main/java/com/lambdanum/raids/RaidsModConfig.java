package com.lambdanum.raids;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid=RaidsMod.MODID)
public class RaidsModConfig {

  public static String HELPER_URL = "https://boiling-forest-57763.herokuapp.com";

  public static boolean CAN_TELEPORT_HOME_FROM_ANY_DIMENSION = true;

  @Mod.EventBusSubscriber
  private static class EventHandler {
    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
      if (event.getModID().equals(RaidsMod.MODID)) {
        ConfigManager.sync(RaidsMod.MODID, Config.Type.INSTANCE);
      }
    }
  }
}
