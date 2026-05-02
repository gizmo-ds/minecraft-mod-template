package com.example.examplemod.api.fabric;

import lombok.experimental.UtilityClass;
import net.fabricmc.loader.api.FabricLoader;

@SuppressWarnings("unused")
@UtilityClass
public class PlatformHelperImpl {
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}