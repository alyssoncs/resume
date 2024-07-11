import alysson.cirilo.resume.utils.getBundle
import alysson.cirilo.resume.utils.getLibrary
import alysson.cirilo.resume.utils.getVersion
import alysson.cirilo.resume.utils.versionCatalog
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm")
    id("alysson.cirilo.resume.quality")
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    val catalog = versionCatalog
    "testRuntimeOnly"(catalog.getLibrary("test.junit.engine"))
    "testImplementation"(catalog.getBundle("unitTest"))
}

project.tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = setOf(TestLogEvent.SKIPPED, TestLogEvent.PASSED, TestLogEvent.FAILED)
        showStandardStreams = true
    }
}

val javaVersion = versionCatalog.getVersion("java").toInt()
java.toolchain.languageVersion = JavaLanguageVersion.of(javaVersion)
kotlin.jvmToolchain(javaVersion)
