plugins {
    alias(libs.plugins.resume.kotlin.library)
    `java-test-fixtures`
}

dependencies {
    api(projects.domain.entities)
    implementation(projects.domain.infra)

    testFixturesImplementation(testFixtures(projects.domain.entities))
    testFixturesImplementation(libs.bundles.unitTest)
}
