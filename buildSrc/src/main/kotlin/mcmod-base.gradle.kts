plugins {
    id("java-library")
    id("architectury-plugin")
    id("dev.architectury.loom-no-remap")
}

val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()

base {
    archivesName.set("${mod.id}-${project.name}-${libs.versions.minecraft.get()}")
}

dependencies {
    minecraft("com.mojang:minecraft:${libs.versions.minecraft.get()}")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(mod.javaVersion))

    withSourcesJar()
}

val copyLicense = copySpec {
    if (!mod.platforms.contains(name.lowercase())) listOf("LICENSE", "NOTICE").forEach { n ->
        from(rootProject.file(n)) { into("") }
        from(rootProject.file("assets/custom/$n")) { rename { n }; into("") }
    }
    from(project.file("third-party-licenses")) {
        into("third-party-licenses")
        exclude("**/.gitkeep")
    }
}

project(":common").extra["copyLicense"] = copyLicense

tasks {
    jar { with(copyLicense) }

    named<Jar>("sourcesJar") { with(copyLicense) }

    processResources { duplicatesStrategy = DuplicatesStrategy.INCLUDE }

    named("clean") { doLast { delete("logs") } }
}