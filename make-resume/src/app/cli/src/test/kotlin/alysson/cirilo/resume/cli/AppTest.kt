package alysson.cirilo.resume.cli

import io.kotest.matchers.shouldNot
import io.kotest.matchers.string.beEmpty
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.net.URL

class AppTest {
    @Test
    fun `should parse args, generate and print resume`() {
        val originalOut = System.out
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        System.setOut(printStream)
        val testResume = "/test-resume.yml".asResource()

        main(arrayOf("-i", testResume.path, "-f", "sober"))

        val output = outputStream.toString().trim()
        output shouldNot beEmpty()
        System.setOut(originalOut)
    }

    private fun String.asResource(): URL {
        return Unit.javaClass.getResource(this)!!
    }
}
