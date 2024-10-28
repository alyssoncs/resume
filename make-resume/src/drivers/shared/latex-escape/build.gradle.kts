import alysson.cirilo.resume.utils.resumeFixtures

plugins {
    alias(libs.plugins.resume.kotlin.library)
    `java-test-fixtures`
}

dependencies {
    api(projects.src.domain.entities)

    testFixturesApi(libs.test.junit.api)
    testFixturesApi(libs.test.junit.params)
    testFixturesApi(resumeFixtures(projects.src.domain.entities))
    testFixturesApi(projects.src.domain.infra)

    testFixturesImplementation(libs.test.kotest.assertions)
}
