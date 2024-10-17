import alysson.cirilo.resume.utils.resumeFixtures

plugins {
    alias(libs.plugins.resume.kotlin.library)
    `java-test-fixtures`
}

dependencies {
    api(projects.src.domain.entities)

    implementation(projects.src.domain.infra)

    testFixturesImplementation(libs.bundles.unitTest)
    testFixturesImplementation(resumeFixtures(projects.src.domain.entities))
}
