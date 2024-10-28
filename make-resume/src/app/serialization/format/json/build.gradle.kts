plugins {
    alias(libs.plugins.resume.kotlin.library)
    alias(libs.plugins.resume.serialization)
}

features {
    unitTests = true
}

dependencies {
    api(projects.src.domain.entities)

    implementation(libs.kotlin.serialization.json)
    implementation(projects.src.app.serialization.kotlinxMapper)
}
