plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(libs.kotlin.gradle)
    implementation(libs.kotlin.serialization.gradle)
    implementation(libs.detekt.gradle)
}
