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
    implementation(libs.dependencies.sorter.gradle)
    implementation(libs.dagp)
}

kotlin.jvmToolchain(libs.versions.java.get().toInt())
