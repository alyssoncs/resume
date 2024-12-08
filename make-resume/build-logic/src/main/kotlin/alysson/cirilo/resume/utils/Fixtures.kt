package alysson.cirilo.resume.utils

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ProjectDependency

internal const val RESUME_GROUP = "alysson.cirilo"

@Suppress("UnstableApiUsage")
fun resumeFixtures(dependency: ProjectDependency): Dependency {
    return dependency.capabilities {
        requireCapability(
            "$RESUME_GROUP:${dependency.dependencyProject.isolated.name}-test-fixtures"
        )
    }
}
