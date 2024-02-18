import alysson.cirilo.resume.utils.getBundle
import alysson.cirilo.resume.utils.getLibrary
import alysson.cirilo.resume.utils.getVersion
import alysson.cirilo.resume.utils.versionCatalog
import gradle.kotlin.dsl.accessors._ae9e46db8ad0dd8613931bdf846ebbab.java

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm")
    id("alysson.cirilo.resume.detekt")
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

val javaVersion = versionCatalog.getVersion("java").toInt()
java.toolchain.languageVersion = JavaLanguageVersion.of(javaVersion)
kotlin.jvmToolchain(javaVersion)
