plugins {
    id("alysson.cirilo.resume.kotlin-library-conventions")
}

dependencies {
    api(projects.domain.entities)
    implementation(projects.domain.infra)
}
