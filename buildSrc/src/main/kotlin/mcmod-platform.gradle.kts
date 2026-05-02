import tools.jackson.databind.ObjectMapper
import tools.jackson.databind.node.ArrayNode
import tools.jackson.databind.node.ObjectNode
import tools.jackson.dataformat.toml.TomlMapper

plugins {
    id("mcmod-base")
    id("com.gradleup.shadow")
}

architectury {
    platformSetupLoomIde()
    loader(project.name)
}

val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()
val platformName = loom.platform.get().displayName()

val common: Configuration by configurations.creating
val shadowBundle: Configuration by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
}
configurations {
    compileOnly.configure { extendsFrom(common) }
    runtimeOnly.configure { extendsFrom(common) }

    configurations.getByName("development$platformName").extendsFrom(common)
}

dependencies {
    common(project(path = ":common")) { isTransitive = false }
    shadowBundle(project(path = ":common", configuration = "transformProduction$platformName"))
}

val generatePlatformResources = tasks.register("generatePlatformResources") {
    description = "generatePlatformResources"

    val outputDir = layout.buildDirectory.dir("generated/platform-resources")
    outputs.dir(outputDir)

    doLast {
        when (project.name) {
            "fabric" -> {
                val file = outputDir.get().file("fabric.mod.json").asFile
                file.parentFile.mkdirs()

                val mapper = ObjectMapper()
                val json = mapper.readTree(
                    file("src/main/resources/fabric.mod.json").reader()
                ) as ObjectNode

                json.let {
                    it.put("id", mod.id)
                    it.put("name", mod.name)
                    it.put("version", mod.version)
                    it.put("description", mod.description)
                    it.put("license", mod.license)
                    it.put("icon", "${mod.id}_logo.png")
                    it.set("authors", mapper.valueToTree<ArrayNode>(mod.authors))
                    it.set("contact", mapper.valueToTree<ObjectNode>(mod.contact))
                }

                mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file.writer(), json)
            }

            "neoforge", "forge" -> {
                val modsFile = if (project.name == "neoforge") "neoforge.mods.toml" else "mods.toml"
                val file = outputDir.get().file("META-INF/$modsFile").asFile
                file.parentFile.mkdirs()

                val mapper = TomlMapper()
                val toml = mapper.readTree(
                    file("src/main/resources/META-INF/$modsFile").reader()
                ) as ObjectNode

                toml.let { root ->
                    root.put("license", mod.license)
                    (root.get("mods").get(0) as ObjectNode).let { mods ->
                        mods.put("modId", mod.id)
                        mods.put("version", mod.version)
                        mods.put("displayName", mod.name)
                        mods.put("description", mod.description)
                        mods.put("logoFile", "${mod.id}_logo.png")
                        mods.put("authors", mod.authors.joinToString(", "))
                        mod.contact.forEach { (key, value) ->
                            when (key) {
                                "homepage" -> mods.put("displayURL", value)
                                "issues" -> root.put("issueTrackerURL", value)
                            }
                        }
                    }
                    if (mod.id != "examplemod") (root.get("dependencies") as ObjectNode).let { it ->
                        it.set(mod.id, it.get("examplemod") as ArrayNode)
                        it.remove("examplemod")
                    }
                }

                mapper.writer()
                    .writeValue(file.writer(), toml)
            }
        }
    }
}

tasks {
    val copyLicense: CopySpec by project(":common").extra

    sourceSets.named("main") { resources.srcDir(generatePlatformResources) }

    processResources {
        dependsOn(generatePlatformResources)

        val expandProps = mapOf(
            "version" to mod.version,
            "group" to mod.group,
            "mod_id" to mod.id,
            "mod_name" to mod.name,
            "license" to mod.license,
        )

        filesMatching(listOf("pack.mcmeta", "*.mixins.json")) {
            expand(expandProps.mapValues { (_, value) -> value.replace("\n", "\\n") })
        }

        inputs.properties(expandProps)

        from(rootProject.file("assets/logo.png")) { rename { "${mod.id}_logo.png" } }
        from(rootProject.file("assets/custom/logo.png")) { rename { "${mod.id}_logo.png" } }
    }

    withType<Jar> { duplicatesStrategy = DuplicatesStrategy.INCLUDE }

    jar { archiveClassifier.set("slim") }

    shadowJar {
        dependsOn(jar)
        with(copyLicense)

        exclude("META-INF/maven/**/*", "META-INF/versions/**/*")

        archiveClassifier.set(null)
        configurations = listOf(shadowBundle)

        mergeServiceFiles()
    }
}