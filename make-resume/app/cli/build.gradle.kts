plugins {
    id("alysson.cirilo.resume.kotlin-application-conventions")
}

dependencies {
    implementation(projects.app.serialization)
    implementation(projects.drivers.latexSober)
    implementation(projects.drivers.latexAwesome)
    implementation(projects.drivers.markdown)

    implementation(libs.kotlinx.cli)
}

application {
    mainClass.set("alysson.cirilo.resume.cli.AppKt")
}
