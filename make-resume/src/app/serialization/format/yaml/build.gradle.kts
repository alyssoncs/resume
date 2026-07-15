plugins {
    alias(libs.plugins.resume.kotlin.library)
    alias(libs.plugins.resume.serialization)
}

features {
    unitTests = true
}

dependencies {
    api(projects.src.domain.entities)

    implementation(projects.src.app.serialization.kotlinxMapper)
    implementation(libs.kotlin.serialization.yaml)

    testImplementation(projects.src.utils.resource)
}
