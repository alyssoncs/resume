package alysson.cirilo.resume.drivers.latexsober

import alysson.cirilo.resume.drivers.utils.date.educationDateFormatter
import alysson.cirilo.resume.drivers.utils.date.workDateFormatter
import alysson.cirilo.resume.drivers.utils.makeDriver
import alysson.cirilo.resume.drivers.utils.resource.asResource
import alysson.cirilo.resume.infra.ResumeDriver

fun makeLatexSoberDriver(): ResumeDriver {
    val template = "/latex-sober-resume-template.tex".asResource()
    val syntaxFactory = LatexSoberSyntaxFactory(
        template,
        "%%content-goes-here%%",
        workDateFormatter,
        educationDateFormatter,
    )
    return makeDriver(syntaxFactory)
}
