import alysson.cirilo.resume.utils.getBundle
import alysson.cirilo.resume.utils.getLibrary
import alysson.cirilo.resume.utils.versionCatalog

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm")
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    val catalog = project.versionCatalog
    add("testRuntimeOnly", catalog.getLibrary("test.junit.engine"))
    add("testImplementation", catalog.getBundle("unitTest"))
}

project.tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
