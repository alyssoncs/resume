plugins {
    alias(libs.plugins.resume.application)
    alias(libs.plugins.jacoco.aggregation)
}

features {
    unitTests = true
}

dependencies {
    implementation(libs.clikt)
    implementation(projects.src.app.serialization.format.json)
    implementation(projects.src.app.serialization.format.yaml)
    implementation(projects.src.drivers.latexAwesome)
    implementation(projects.src.drivers.latexSober)
    implementation(projects.src.drivers.markdown)
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