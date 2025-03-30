import alysson.cirilo.resume.utils.getLibrary
import alysson.cirilo.resume.utils.versionCatalog

plugins {
    id("io.gitlab.arturbosch.detekt")
}

detekt {
    parallel = true
    buildUponDefaultConfig = true
    config.setFrom("${rootDir}/config/detekt/detekt.yml")
    source.from(
        "src/testFixtures/java",
        "src/testFixtures/kotlin",
    )
}

dependencies {
    val catalog = project.versionCatalog
    detektPlugins(catalog.getLibrary("detekt.formatting"))
}
