package com.example.examplemod.api.forge;

import lombok.experimental.UtilityClass;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

@SuppressWarnings("unused")
@UtilityClass
public class PlatformHelperImpl {
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }
}