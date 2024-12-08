package alysson.cirilo.resume.serialization.yaml

import alysson.cirilo.resume.entities.Resume
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import org.junit.jupiter.api.Test

class YamlDeserializerTest {

    @Test
    fun `valid yaml should not throw`() {
        shouldNotThrow<Throwable> {
            resume("test-resume.yml")
        }
    }

    @Test
    fun `missing yaml bracket fails`() {
        shouldThrow<Throwable> {
            malformedResume("missing-colon.yml")
        }
    }

    private fun resume(path: String): Resume {
        val malformedYaml = "/valid/$path".read()
        return deserializeYaml(malformedYaml)
    }

    private fun malformedResume(path: String): Resume {
        val malformedYaml = "/malformed/$path".read()
        return deserializeYaml(malformedYaml)
    }

    private fun String.read(): String {
        return Unit.javaClass.getResource(this)!!.readText()
    }
}
