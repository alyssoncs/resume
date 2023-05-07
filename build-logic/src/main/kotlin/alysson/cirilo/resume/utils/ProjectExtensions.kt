package alysson.cirilo.resume.utils

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension

val Project.versionCatalog: VersionCatalog
    get() = this.extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
