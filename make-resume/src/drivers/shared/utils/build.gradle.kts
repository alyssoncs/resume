plugins {
    alias(libs.plugins.resume.kotlin.library)
    `java-test-fixtures`
}

features {
    unitTests = true
}

dependencies {
    api(projects.src.domain.entities)
    api(projects.src.domain.infra)

    testFixturesApi(libs.test.junit.api)
    testFixturesImplementation(libs.test.kotest.assertions)
    testFixturesImplementation(resumeFixtures(projects.src.domain.entities))

    testImplementation(resumeFixtures(projects.src.domain.entities))
}
