package alysson.cirilo.resume.drivers.latexawesome

import alysson.cirilo.resume.drivers.utils.makeAgnosticDriver
import alysson.cirilo.resume.drivers.utils.resource.asResource
import alysson.cirilo.resume.infra.ResumeDriver

fun makeLatexAwesomeDriver(): ResumeDriver {
    fun String.asPlaceholder() = "%%${this}%%"
    val syntaxFactory = LatexAwesomeSyntaxFactory(
        template = "/latex-awesome-resume-template.tex".asResource(),
        headerPlaceholder = "header".asPlaceholder(),
        contentPlaceholder = "content-goes-here".asPlaceholder()
    )
    return makeAgnosticDriver(syntaxFactory)
}
