dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
