plugins {
    alias(libs.plugins.resume.kotlin.library)
}

dependencies {
    api(projects.domain.entities)
}
