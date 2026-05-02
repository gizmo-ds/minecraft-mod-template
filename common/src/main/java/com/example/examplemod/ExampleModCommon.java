package com.example.examplemod;

import com.example.examplemod.api.PlatformHelper;

public class ExampleModCommon {
    public static void init() {
        Constants.LOG.info("CurrentTarget: {}", PlatformHelper.getCurrentTarget());
        Constants.LOG.info("EnvironmentName: {}", PlatformHelper.getEnvironmentName());
    }
}