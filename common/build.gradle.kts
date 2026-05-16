plugins {
    id("mcmod-base")
}

architectury {
    common(mod.platforms)
}

dependencies {
    modImplementation(libs.fabric.loader)

    testImplementation(platform("org.junit:junit-bom:6.0.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}