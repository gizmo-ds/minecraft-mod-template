plugins {
    id("mcmod-platform")
}

loom.forge {
    mixinConfig("examplemod.mixins.json")
    mixinConfig("examplemod.forge.mixins.json")
}

dependencies {
    @Suppress("USELESS_IS_CHECK")
    if (libs.forge is Provider<*>) forge(libs.create("forge")) else forge(libs.forge)
}