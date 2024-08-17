plugins {
    alias(libs.plugins.resume.kotlin.library)
}

dependencies {
    api(projects.src.domain.entities)
}
