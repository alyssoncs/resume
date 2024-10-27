import com.autonomousapps.DependencyAnalysisSubExtension

plugins {
    id("alysson.cirilo.resume.detekt")
    id("com.squareup.sort-dependencies")
    id("com.autonomousapps.dependency-analysis")
}

extensions.configure<DependencyAnalysisSubExtension> {
    issues {
        onAny {
            severity("fail")
        }
    }
}
