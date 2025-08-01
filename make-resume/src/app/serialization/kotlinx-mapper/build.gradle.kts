plugins {
    alias(libs.plugins.resume.kotlin.library)
}

features {
    unitTests = true
}

dependencies {
    api(projects.src.app.serialization.kotlinxModel)
    api(projects.src.domain.entities)

    testImplementation(resumeFixtures(projects.src.app.serialization.kotlinxModel))
    testImplementation(resumeFixtures(projects.src.domain.entities))
}
