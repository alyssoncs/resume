plugins {
    alias(libs.plugins.resume.kotlin.library)
    alias(libs.plugins.resume.serialization)
}

dependencies {
    implementation(libs.kotlin.serialization.json)
}
