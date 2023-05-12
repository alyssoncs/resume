plugins {
    id("alysson.cirilo.resume.kotlin-library-conventions")
}

dependencies {
    api(projects.domain.infra)
    testImplementation(projects.domain.test.dataBuilders)
}
