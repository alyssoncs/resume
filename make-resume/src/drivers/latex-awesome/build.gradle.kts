plugins {
    alias(libs.plugins.resume.kotlin.library)
}

features {
    unitTests = true
}

dependencies {
    api(projects.src.domain.infra)

    implementation(projects.src.drivers.shared.latexEscape)
    implementation(projects.src.drivers.shared.utils)
    implementation(projects.src.utils.resource)

    testImplementation(resumeFixtures(projects.src.domain.entities))
    testImplementation(resumeFixtures(projects.src.drivers.shared.latexEscape))
    testImplementation(resumeFixtures(projects.src.drivers.shared.utils))
}
