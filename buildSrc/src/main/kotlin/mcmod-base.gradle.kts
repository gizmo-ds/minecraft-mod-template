plugins {
    id("java-library")
    id("architectury-plugin")
    id("dev.architectury.loom")
}

val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()
val mcVersion = libs.versions.minecraft.get()

base {
    archivesName.set("${mod.id}-${project.name}-$mcVersion")
}

@Suppress("UnstableApiUsage")
repositories {
    exclusiveContent {
        forRepository {
            maven("https://maven.parchmentmc.org/") { name = "ParchmentMC" }
        }
        filter {
            includeGroupAndSubgroups("org.parchmentmc.data")
        }
    }
}

dependencies {
    minecraft("net.minecraft:minecraft:$mcVersion")
    @Suppress("UnstableApiUsage")
    "mappings"(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-$mcVersion:${libs.versions.parchment.get()}@zip")
    })
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