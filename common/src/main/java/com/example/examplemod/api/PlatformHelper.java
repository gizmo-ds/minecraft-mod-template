package com.example.examplemod.api;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.targets.ArchitecturyTarget;
import lombok.experimental.UtilityClass;

@SuppressWarnings("unused")
@UtilityClass
public class PlatformHelper {
    public String getCurrentTarget() {
        return ArchitecturyTarget.getCurrentTarget();
    }

    @ExpectPlatform
    public boolean isModLoaded(String modId) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public boolean isDevelopmentEnvironment() {
        throw new AssertionError();
    }

    public String getEnvironmentName() {
        return isDevelopmentEnvironment() ? "development" : "production";
    }
}
