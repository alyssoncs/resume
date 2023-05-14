package alysson.cirilo.resume.drivers.latexsober

import alysson.cirilo.resume.drivers.utils.makeAgnosticDriver
import alysson.cirilo.resume.drivers.utils.resource.asResource
import alysson.cirilo.resume.infra.ResumeDriver

fun makeLatexSoberDriver(): ResumeDriver {
    val template = "/latex-sober-resume-template.tex".asResource()
    val syntaxFactory = LatexSoberSyntaxFactory(template, "%%content-goes-here%%")
    return makeAgnosticDriver(syntaxFactory)
}
