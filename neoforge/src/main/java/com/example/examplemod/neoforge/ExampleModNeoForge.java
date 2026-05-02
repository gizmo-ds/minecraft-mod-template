package com.example.examplemod.neoforge;

import com.example.examplemod.Constants;
import com.example.examplemod.ExampleModCommon;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class ExampleModNeoForge {
    public ExampleModNeoForge(IEventBus ignoredEventBus) {
        ExampleModCommon.init();
    }
}