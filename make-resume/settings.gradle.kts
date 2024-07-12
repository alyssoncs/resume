enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")

    repositories {
        gradlePluginPortal()
        // for detekt snapshot
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
        }
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        // for detekt snapshot
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
        }
    }
}

plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}

rootProject.name = "make-resume"
include(":app:cli")
include(":app:serialization")
include(":domain:entities")
include(":domain:infra")
include(":drivers:latex-awesome")
include(":drivers:latex-sober")
include(":drivers:markdown")
include(":drivers:shared:latex-escape")
include(":drivers:shared:utils")
