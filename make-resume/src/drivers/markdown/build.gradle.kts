plugins {
    alias(libs.plugins.resume.kotlin.library)
}

features {
    unitTests = true
}

dependencies {
    api(projects.src.domain.infra)
    implementation(projects.src.drivers.shared.utils)

    testImplementation(resumeFixtures(projects.src.drivers.shared.utils))
}
