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

// shadow gradle example
dependencies.implementation("net.objecthunter:exp4j:0.4.8").let { if (it != null) dependencies.shadowBundle(it) }
tasks.shadowJar {
    relocate("net.objecthunter.exp4j", "${mod.group}.libs.exp4j")
}