package com.example.examplemod.fabric;

import com.example.examplemod.ExampleModCommon;
import net.fabricmc.api.ModInitializer;

public class ExampleModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ExampleModCommon.init();
    }
}