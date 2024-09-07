package alysson.cirilo.resume.drivers.latexsober

import alysson.cirilo.resume.drivers.latex.escape.LatexDriverTest
import alysson.cirilo.resume.entities.aResume
import io.kotest.matchers.string.shouldContain
import java.time.format.DateTimeFormatter

class LatexSoberDriverTest : LatexDriverTest() {

    override fun makeLatexDriver(workDateFormatter: DateTimeFormatter, educationDateFormatter: DateTimeFormatter) =
        makeLatexSoberDriver(
            template = """
                \begin{document}
                <content>
                \end{document}
            """.trimIndent(),
            contentPlaceholder = "<content>",
            workDateFormatter = workDateFormatter,
            educationDateFormatter = educationDateFormatter,
        )

    @LatexEscapeParams
    fun `should escape name`(raw: String, escaped: String) {
        val resume = aResume().from(raw)

        val output = driver.convert(resume)

        output shouldContain escaped
    }
}
