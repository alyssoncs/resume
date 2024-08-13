import alysson.cirilo.resume.utils.resumeFixtures

plugins {
    alias(libs.plugins.resume.kotlin.library)
}

dependencies {
    api(projects.app.serialization.kotlinxModel)
    api(projects.domain.entities)

    testImplementation(resumeFixtures(projects.app.serialization.kotlinxModel))
    testImplementation(resumeFixtures(projects.domain.entities))
}
