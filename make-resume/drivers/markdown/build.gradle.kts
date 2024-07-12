import alysson.cirilo.resume.utils.resumeFixtures

plugins {
    alias(libs.plugins.resume.kotlin.library)
}

dependencies {
    api(projects.domain.infra)
    implementation(projects.drivers.shared.utils)

    testImplementation(resumeFixtures(projects.drivers.shared.utils))
}
