package alysson.cirilo.resume.drivers.latexsober

import alysson.cirilo.resume.drivers.latex.escape.LatexDriverTest
import java.time.format.DateTimeFormatter

class LatexSoberDriverTest : LatexDriverTest() {

    override fun makeLatexDriver(
        workDateFormatter: DateTimeFormatter,
        educationDateFormatter: DateTimeFormatter,
    ) = makeLatexSoberDriver(
        template = """
            \begin{document}
            <content>
            \end{document}
        """.trimIndent(),
        contentPlaceholder = "<content>",
        workDateFormatter = workDateFormatter,
        educationDateFormatter = educationDateFormatter,
    )
}
