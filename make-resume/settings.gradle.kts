enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

rootProject.name = "make-resume"
include(":src:app:cli")
include(":src:app:serialization:format:json")
include(":src:app:serialization:format:yaml")
include(":src:app:serialization:kotlinx-mapper")
include(":src:app:serialization:kotlinx-model")
include(":src:domain:entities")
include(":src:domain:infra")
include(":src:drivers:latex-awesome")
include(":src:drivers:latex-sober")
include(":src:drivers:markdown")
include(":src:drivers:shared:latex-escape")
include(":src:drivers:shared:utils")
