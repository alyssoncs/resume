import alysson.cirilo.resume.utils.resumeFixtures

plugins {
    alias(libs.plugins.resume.kotlin.library)
}

dependencies {
    api(projects.domain.infra)

    implementation(projects.drivers.shared.latexEscape)
    implementation(projects.drivers.shared.utils)

    testImplementation(resumeFixtures(projects.domain.entities))
    testImplementation(resumeFixtures(projects.drivers.shared.latexEscape))
    testImplementation(resumeFixtures(projects.drivers.shared.utils))
}
