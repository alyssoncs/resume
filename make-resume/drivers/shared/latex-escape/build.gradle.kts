plugins {
    alias(libs.plugins.resume.kotlin.library)
    `java-test-fixtures`
}

dependencies {
    api(projects.domain.entities)

    testFixturesImplementation(testFixtures(projects.domain.entities))
    testFixturesImplementation(projects.domain.infra)
    testFixturesImplementation(libs.bundles.unitTest)
}
