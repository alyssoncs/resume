package alysson.cirilo.resume.drivers.latexawesome

import alysson.cirilo.resume.drivers.latex.escape.LatexDriverTest
import alysson.cirilo.resume.entities.aResume
import io.kotest.matchers.string.shouldContain
import java.time.format.DateTimeFormatter

class LatexAwesomeDriverTest : LatexDriverTest() {

    override fun makeLatexDriver(workDateFormatter: DateTimeFormatter, educationDateFormatter: DateTimeFormatter) =
        makeLatexAwesomeDriver(
            template = """
                <header>
                \begin{document}
                <content>
                \end{document}
            """.trimIndent(),
            headerPlaceholder = "<header>",
            contentPlaceholder = "<content>",
            workDateFormatter = workDateFormatter,
            educationDateFormatter = educationDateFormatter,
        )

    @LatexEscapeParams
    fun `should escape name`(raw: String, escaped: String) {
        val resume = aResume().from(raw)

        val output = driver.convert(resume)

        output shouldContain escaped.split(" ", limit = 2).joinToString("") { "{$it}" }
    }
}
