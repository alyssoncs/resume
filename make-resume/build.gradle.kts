plugins {
    alias(libs.plugins.dagp)
    alias(libs.plugins.kotlin.jvm) apply false
}

dependencyAnalysis {
    usage {
        analysis {
            checkSuperClasses(true)
        }
    }
    structure {
        bundle("junit") {
            includeGroup("org.junit.jupiter")
        }

        bundle("kotlinx-serialization") {
            include("org.jetbrains.kotlinx:kotlinx-serialization.*")
        }

        bundle("kotest-assertions") {
            include("io.kotest:kotest-assertions.*")
        }
    }
}
