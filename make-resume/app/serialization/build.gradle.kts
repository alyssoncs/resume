plugins {
    alias(libs.plugins.resume.kotlin.library)
    id("alysson.cirilo.resume.kotlin-serialization-conventions")
}

dependencies {
    api(projects.domain.entities)

    implementation(libs.kotlin.serialization.json)

    testImplementation(testFixtures(projects.domain.entities))
}
