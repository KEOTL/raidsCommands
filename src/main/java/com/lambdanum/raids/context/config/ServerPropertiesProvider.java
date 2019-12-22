package com.lambdanum.raids.context.config;

import com.lambdanum.raids.RaidsModConfig;
import com.lambdanum.raids.infrastructure.injection.RuntimeProvider;

public class ServerPropertiesProvider implements RuntimeProvider<ServerProperties> {
  @Override
  public ServerProperties get() {
    return new ServerProperties(RaidsModConfig.HELPER_URL);
  }
}
