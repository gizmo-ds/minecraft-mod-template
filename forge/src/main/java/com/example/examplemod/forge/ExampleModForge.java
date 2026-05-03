package com.example.examplemod.forge;

import com.example.examplemod.Constants;
import com.example.examplemod.ExampleModCommon;
import net.minecraftforge.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class ExampleModForge {
    public ExampleModForge() {
        ExampleModCommon.init();
    }
}