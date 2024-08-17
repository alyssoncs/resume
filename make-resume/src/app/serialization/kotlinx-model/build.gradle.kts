plugins {
    alias(libs.plugins.resume.kotlin.library)
    alias(libs.plugins.resume.serialization)
    `java-test-fixtures`
}

dependencies {
    implementation(libs.kotlin.serialization.json)
}
