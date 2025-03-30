dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}
