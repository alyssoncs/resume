package alysson.cirilo.resume.drivers.utils

import alysson.cirilo.resume.entities.aResume
import alysson.cirilo.resume.infra.ResumeDriver
import io.kotest.assertions.throwables.shouldNotThrow
import org.junit.jupiter.api.Test

abstract class ResumeBuilderEntrypointTest {
    abstract fun makeResumeDriver(): ResumeDriver

    @Test
    fun `should create a working driver`() {
        shouldNotThrow<Throwable> {
            val driver = makeResumeDriver()
            driver.convert(aResume().build())
        }
    }
}
