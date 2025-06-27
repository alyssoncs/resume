package alysson.cirilo.resume.utils

import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ProjectDependency

open class FixtureExtension(
    private val project: Project,
) {
    @Suppress("UnstableApiUsage")
    operator fun invoke(dependency: ProjectDependency): Dependency {
        val projectName = project.project(dependency.path).isolated.name
        return dependency.capabilities {
            requireCapability(
                "$RESUME_GROUP:$projectName-test-fixtures"
            )
        }
    }
}
