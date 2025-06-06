package alysson.cirilo.resume.drivers.latexawesome

import alysson.cirilo.resume.drivers.latex.escape.escapeLatex
import alysson.cirilo.resume.drivers.utils.date.educationDateFormatter
import alysson.cirilo.resume.drivers.utils.date.workDateFormatter
import alysson.cirilo.resume.drivers.utils.makeDriver
import alysson.cirilo.resume.infra.ResumeDriver
import alysson.cirilo.resume.utils.resource.read
import java.time.format.DateTimeFormatter

fun makeLatexAwesomeDriver(): ResumeDriver {
    fun String.asPlaceholder() = "%%$this%%"
    return makeLatexAwesomeDriver(
        "/latex-awesome-resume-template.tex".read(),
        "header".asPlaceholder(),
        "content-goes-here".asPlaceholder(),
        workDateFormatter = workDateFormatter,
        educationDateFormatter = educationDateFormatter,
    )
}

internal fun makeLatexAwesomeDriver(
    template: String,
    headerPlaceholder: String,
    contentPlaceholder: String,
    workDateFormatter: DateTimeFormatter,
    educationDateFormatter: DateTimeFormatter,
): ResumeDriver {
    val resumeBuilder = LatexAwesomeResumeBuilder(
        template = template,
        headerPlaceholder = headerPlaceholder,
        contentPlaceholder = contentPlaceholder,
        workDateFormatter = workDateFormatter,
        educationDateFormatter = educationDateFormatter,
    )
    return makeDriver(resumeBuilder) { resume ->
        resume.escapeLatex()
    }
}
