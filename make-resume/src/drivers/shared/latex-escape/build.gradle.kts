plugins {
    alias(libs.plugins.resume.kotlin.library)
    `java-test-fixtures`
}

dependencies {
    api(projects.src.domain.entities)

    testFixturesApi(projects.src.domain.infra)
    testFixturesApi(libs.test.junit.api)
    testFixturesApi(libs.test.junit.params)
    testFixturesApi(resumeFixtures(projects.src.domain.entities))

    testFixturesImplementation(libs.test.kotest.assertions)
}
