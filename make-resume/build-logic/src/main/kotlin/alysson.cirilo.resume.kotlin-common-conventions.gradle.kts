import alysson.cirilo.resume.utils.RESUME_GROUP
import alysson.cirilo.resume.utils.getBundle
import alysson.cirilo.resume.utils.getVersion
import alysson.cirilo.resume.utils.versionCatalog
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm")
    `jvm-test-suite`
    id("alysson.cirilo.resume.quality")
}

project.group = RESUME_GROUP

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter(versionCatalog.getVersion("junit"))

            dependencies {
                implementation.bundle(versionCatalog.getBundle("unitTest"))
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

val javaVersion = versionCatalog.getVersion("java").toInt()
kotlin.jvmToolchain(javaVersion)
