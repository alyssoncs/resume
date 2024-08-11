import alysson.cirilo.resume.utils.resumeFixtures

plugins {
    alias(libs.plugins.resume.kotlin.library)
    alias(libs.plugins.resume.serialization)
}

dependencies {
    api(projects.domain.entities)

    implementation(projects.app.serialization.kotlinxModel)
    implementation(libs.kotlin.serialization.json)

    testImplementation(resumeFixtures(projects.domain.entities))
}
