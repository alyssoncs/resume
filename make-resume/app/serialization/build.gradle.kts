plugins {
    id("alysson.cirilo.resume.kotlin-library-conventions")
    id("alysson.cirilo.resume.kotlin-serialization-conventions")
    //alias(libs.plugins.kotlin.serialization)
}

dependencies {
    api(projects.domain.entities)

    implementation(libs.kotlin.serialization.json)
}
