plugins {
    id("alysson.cirilo.resume.kotlin-library-conventions")
}

dependencies {
    api(projects.domain.infra)
    implementation(projects.drivers.shared.utils)
    testImplementation(projects.drivers.shared.test)
}
