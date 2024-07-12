import alysson.cirilo.resume.utils.resumeFixtures

plugins {
    alias(libs.plugins.resume.kotlin.library)
    `java-test-fixtures`
}

dependencies {
    api(projects.domain.entities)

    testFixturesImplementation(resumeFixtures(projects.domain.entities))
    testFixturesImplementation(libs.bundles.unitTest)
    testFixturesImplementation(projects.domain.infra)
}
