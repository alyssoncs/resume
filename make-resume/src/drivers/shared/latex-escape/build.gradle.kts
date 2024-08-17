import alysson.cirilo.resume.utils.resumeFixtures

plugins {
    alias(libs.plugins.resume.kotlin.library)
    `java-test-fixtures`
}

dependencies {
    api(projects.src.domain.entities)

    testFixturesImplementation(resumeFixtures(projects.src.domain.entities))
    testFixturesImplementation(libs.bundles.unitTest)
    testFixturesImplementation(projects.src.domain.infra)
}
