plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.kotlin.gradle)
    implementation(libs.kotlin.serialization.gradle)
    implementation(libs.detekt.gradle)
    implementation(libs.dependencies.sorter.gradle)
}
