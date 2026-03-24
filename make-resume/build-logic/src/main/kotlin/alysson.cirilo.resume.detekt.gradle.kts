import alysson.cirilo.resume.utils.getLibrary
import alysson.cirilo.resume.utils.versionCatalog

plugins {
    id("dev.detekt")
}

detekt {
    parallel = true
    buildUponDefaultConfig = true
    config.setFrom("${rootDir}/config/detekt/detekt.yml")
    source.from(
        "src/testFixtures/java",
        "src/testFixtures/kotlin",
    )
}

dependencies {
    val catalog = project.versionCatalog
    detektPlugins(catalog.getLibrary("detekt.ktlint.wrapper.plugin"))
}

tasks.named("check") {
    dependsOn("detektMain")
    dependsOn("detektTest")
}

pluginManager.withPlugin("org.gradle.java-test-fixtures") {
    tasks.named("check") {
        dependsOn("detektTestFixtures")
    }
}
