import alysson.cirilo.resume.utils.resumeFixtures

plugins {
    alias(libs.plugins.resume.kotlin.library)
    alias(libs.plugins.resume.serialization)
}

dependencies {
    api(projects.src.domain.entities)

    implementation(projects.src.app.serialization.kotlinxMapper)
    implementation(libs.kotlin.serialization.yaml)

    testImplementation(resumeFixtures(projects.src.domain.entities))
}
