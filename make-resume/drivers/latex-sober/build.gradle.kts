plugins {
    alias(libs.plugins.resume.kotlin.library)
}

dependencies {
    api(projects.domain.infra)
    implementation(projects.drivers.shared.utils)

    testImplementation(testFixtures(projects.drivers.shared.utils))
}
