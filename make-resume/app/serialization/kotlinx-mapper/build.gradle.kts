plugins {
    alias(libs.plugins.resume.kotlin.library)
}

dependencies {
    api(projects.app.serialization.kotlinxModel)
    api(projects.domain.entities)
}
