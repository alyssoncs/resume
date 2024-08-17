import alysson.cirilo.resume.utils.resumeFixtures

plugins {
    alias(libs.plugins.resume.kotlin.library)
}

dependencies {
    api(projects.src.domain.infra)
    implementation(projects.src.drivers.shared.utils)

    testImplementation(resumeFixtures(projects.src.drivers.shared.utils))
}
