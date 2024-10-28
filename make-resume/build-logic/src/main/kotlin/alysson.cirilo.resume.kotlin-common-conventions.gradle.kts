import alysson.cirilo.resume.utils.RESUME_GROUP
import alysson.cirilo.resume.utils.getLibrary
import alysson.cirilo.resume.utils.getVersion
import alysson.cirilo.resume.utils.versionCatalog
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm")
    id("alysson.cirilo.resume.quality")
}

project.group = RESUME_GROUP

val javaVersion = versionCatalog.getVersion("java").toInt()
kotlin.jvmToolchain(javaVersion)

open class FeaturesExtension(
    private val objects: ObjectFactory,
    private val enableTests: () -> Unit,
) {
    private val unitTestsProperty = objects.property<Boolean>().convention(false)

    var unitTests: Boolean
        get() = unitTestsProperty.get()
        set(value) {
            unitTestsProperty = value
            unitTestsProperty.disallowChanges()
            if (unitTestsProperty.get()) {
                enableTests()
            }
        }
}

extensions.create<FeaturesExtension>("features", objects, ::enableTests)

fun enableTests() {
    @Suppress("UnstableApiUsage")
    configure<TestingExtension> {
        suites {
            val test by getting(JvmTestSuite::class) {
                useJUnitJupiter(versionCatalog.getVersion("junit"))

                dependencies {
                    implementation(versionCatalog.getLibrary("test.kotest.assertions"))
                }

                targets.all {
                    testTask.configure {
                        testLogging {
                            exceptionFormat = TestExceptionFormat.FULL
                            events = setOf(
                                TestLogEvent.SKIPPED,
                                TestLogEvent.PASSED,
                                TestLogEvent.FAILED,
                                TestLogEvent.STANDARD_OUT,
                                TestLogEvent.STANDARD_ERROR,
                            )
                            showStandardStreams = true
                        }
                    }
                }
            }
        }
    }
}
