plugins {
    alias(libs.plugins.resume.application)
}

dependencies {
    implementation(projects.app.serialization)
    implementation(projects.drivers.latexSober)
    implementation(projects.drivers.latexAwesome)
    implementation(projects.drivers.markdown)

    implementation(libs.kotlinx.cli)
}

private val applicationClass = "alysson.cirilo.resume.cli.AppKt"

application {
    mainClass.set(applicationClass)
}

tasks.withType(Jar::class.java) {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = applicationClass
    }
}

tasks.register<Jar>("uberJar") {
    archiveClassifier.set("uber")

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}