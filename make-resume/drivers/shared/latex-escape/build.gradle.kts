plugins {
    alias(libs.plugins.resume.kotlin.library)
    `java-test-fixtures`
}

dependencies {
    testFixturesImplementation(testFixtures(projects.domain.entities))
    testFixturesImplementation(projects.domain.infra)
    testFixturesImplementation(libs.bundles.unitTest)
}
