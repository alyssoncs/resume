package alysson.cirilo.resume.cli

import alysson.cirilo.resume.utils.resource.asResource
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.kotest.matchers.string.beEmpty
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class AppTest {
    @Test
    fun `should parse args, generate and print resume`() {
        val testResume = "/test-resume.yml".asResource()

        val output = capture(System.out) {
            main(arrayOf("-i", testResume.path, "-f", "sober"))
        }

        output shouldNot beEmpty()
    }

    @Test
    fun `should print to stderr on missing args`() {
        val exitProcess = ExitProcessSpy()

        val output = capture(System.err) {
            app(emptyArray(), exitProcess)
        }

        output shouldNot beEmpty()
        exitProcess.wasCalledWith(1) shouldBe true
    }

    private fun capture(stream: PrintStream, block: () -> Unit): String {
        val outputStream = ByteArrayOutputStream()
        val testStream = PrintStream(outputStream)

        val setStream = when (stream) {
            System.out -> System::setOut
            System.err -> System::setErr
            else -> throw IllegalArgumentException("Unsupported stream: $stream")
        }

        setStream(testStream)
        block()
        setStream(stream)

        return outputStream.toString().trim()
    }

    class ExitProcessSpy : (Int) -> Unit {
        private var exitCode: Int? = null

        fun wasCalledWith(code: Int): Boolean {
            return exitCode == code
        }

        override fun invoke(code: Int) {
            exitCode = code
        }
    }
}
