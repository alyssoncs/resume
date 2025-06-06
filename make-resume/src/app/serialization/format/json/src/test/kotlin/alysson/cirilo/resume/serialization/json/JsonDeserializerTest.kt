package alysson.cirilo.resume.serialization.json

import alysson.cirilo.resume.entities.Resume
import alysson.cirilo.resume.utils.resource.read
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import org.junit.jupiter.api.Test

class JsonDeserializerTest {

    @Test
    fun `valid json should not throw`() {
        shouldNotThrow<Throwable> {
            resume("test-resume.json")
        }
    }

    @Test
    fun `missing json bracket fails`() {
        shouldThrow<Throwable> {
            malformedResume("missing-bracket.json")
        }
    }

    private fun resume(path: String): Resume {
        val malformedJson = "/valid/$path".read()
        return deserializeJson(malformedJson)
    }

    private fun malformedResume(path: String): Resume {
        val malformedJson = "/malformed/$path".read()
        return deserializeJson(malformedJson)
    }
}
