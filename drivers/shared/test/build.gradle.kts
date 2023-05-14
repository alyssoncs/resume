plugins {
    id("alysson.cirilo.resume.kotlin-library-conventions")
}

dependencies {
    api(projects.drivers.shared.utils)
    implementation(projects.domain.test.dataBuilders)
    implementation(libs.bundles.unitTest)
}
