plugins {
    id("mcmod-platform")
}

repositories {
    maven("https://maven.terraformersmc.com/") { name = "Terraformers" }
}

dependencies {
    implementation(libs.fabric.loader)

    runtimeOnly(libs.fabric.api)
    runtimeOnly(libs.fabric.modmenu)
}

// The following code demonstrates how to use the Shadow Gradle plugin.
// In most cases, you can safely remove it :)
dependencies.implementation("net.objecthunter:exp4j:0.4.8").let { if (it != null) dependencies.shadowBundle(it) }
tasks.shadowJar {
    relocate("net.objecthunter.exp4j", "${mod.group}.libs.exp4j")
}