package com.example.examplemod.api.neoforge;

import lombok.experimental.UtilityClass;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

@SuppressWarnings("unused")
@UtilityClass
public class PlatformHelperImpl {
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.getCurrent().isProduction();
    }
}