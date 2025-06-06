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
        val originalOut = System.out
        val printStream = testStream()
        System.setOut(printStream)
        val testResume = "/test-resume.yml".asResource()

        main(arrayOf("-i", testResume.path, "-f", "sober"))

        val output = printStream.toString().trim()
        output shouldNot beEmpty()
        System.setOut(originalOut)
    }

    @Test
    fun `should print to stderr on missing args`() {
        val originalErr = System.err
        val printStream = testStream()
        System.setErr(printStream)
        val exitProcess = ExitProcessSpy()

        app(emptyArray(), exitProcess)

        val output = printStream.toString().trim()
        output shouldNot beEmpty()
        exitProcess.wasCalledWith(1) shouldBe true
        System.setErr(originalErr)
    }

    private fun testStream(): PrintStream {
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        return printStream
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
