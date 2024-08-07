import alysson.cirilo.resume.utils.RESUME_GROUP
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

dependencies {
    val catalog = versionCatalog
    "testRuntimeOnly"(catalog.getLibrary("test.junit.engine"))
    "testRuntimeOnly"(catalog.getLibrary("test.junit.launcher"))
    "testImplementation"(catalog.getBundle("unitTest"))
}

project.group = RESUME_GROUP

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
