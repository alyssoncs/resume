import alysson.cirilo.resume.utils.resumeFixtures

plugins {
    alias(libs.plugins.resume.kotlin.library)
    `java-test-fixtures`
}

dependencies {
    api(projects.domain.entities)
    implementation(projects.domain.infra)

    testFixturesImplementation(resumeFixtures(projects.domain.entities))
    testFixturesImplementation(libs.bundles.unitTest)
}
