package alysson.cirilo.resume.utils

import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.provider.Provider

fun VersionCatalog.getLibrary(catalogAlias: String): Provider<MinimalExternalModuleDependency> {
    return this.findLibrary(catalogAlias).get()
}

fun VersionCatalog.getBundle(catalogAlias: String): Provider<ExternalModuleDependencyBundle> {
    return this.findBundle(catalogAlias).get()
}

fun VersionCatalog.getVersion(catalogAlias: String): String {
    return this.findVersion(catalogAlias).get().requiredVersion
}
