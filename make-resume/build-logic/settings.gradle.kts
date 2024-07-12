dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        // for detekt snapshot
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
        }
    }

    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
