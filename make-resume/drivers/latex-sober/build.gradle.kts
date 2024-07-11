plugins {
    alias(libs.plugins.resume.kotlin.library)
}

dependencies {
    api(projects.domain.infra)

    implementation(projects.drivers.shared.latexEscape)
    implementation(projects.drivers.shared.utils)

    testImplementation(testFixtures(projects.domain.entities))
    testImplementation(testFixtures(projects.drivers.shared.latexEscape))
    testImplementation(testFixtures(projects.drivers.shared.utils))
}
