plugins {
    alias(libs.plugins.resume.kotlin.library)
    alias(libs.plugins.resume.serialization)
}

dependencies {
    api(projects.domain.entities)

    implementation(libs.kotlin.serialization.json)

    testImplementation(testFixtures(projects.domain.entities))
}
