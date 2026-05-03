package com.example.examplemod.fabric;

import com.example.examplemod.Constants;
import net.fabricmc.api.ClientModInitializer;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ExampleModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // The following code demonstrates how to use the Shadow Gradle plugin.
        // In most cases, you can safely remove it :)
        Expression e = new ExpressionBuilder("3 * sin(y) - 2 / (x - 2)")
                .variables("x", "y")
                .build()
                .setVariable("x", 2.3)
                .setVariable("y", 3.14);
        double result = e.evaluate();
        Constants.LOG.info("Result: {}", result);
    }
}