import alysson.cirilo.resume.utils.resumeFixtures

plugins {
    alias(libs.plugins.resume.kotlin.library)
    alias(libs.plugins.resume.serialization)
}

dependencies {
    api(projects.src.domain.entities)

    implementation(libs.kotlin.serialization.json)
    implementation(projects.src.app.serialization.kotlinxMapper)

    testImplementation(resumeFixtures(projects.src.domain.entities))
}
