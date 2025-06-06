package alysson.cirilo.resume.drivers.latexsober

import alysson.cirilo.resume.drivers.latex.escape.escapeLatex
import alysson.cirilo.resume.drivers.utils.date.educationDateFormatter
import alysson.cirilo.resume.drivers.utils.date.workDateFormatter
import alysson.cirilo.resume.drivers.utils.makeDriver
import alysson.cirilo.resume.infra.ResumeDriver
import alysson.cirilo.resume.utils.resource.asResource
import java.time.format.DateTimeFormatter

fun makeLatexSoberDriver(): ResumeDriver {
    return makeLatexSoberDriver(
        template = "/latex-sober-resume-template.tex".asResource(),
        contentPlaceholder = "%%content-goes-here%%",
        workDateFormatter = workDateFormatter,
        educationDateFormatter = educationDateFormatter,
    )
}

internal fun makeLatexSoberDriver(
    template: String,
    contentPlaceholder: String,
    workDateFormatter: DateTimeFormatter,
    educationDateFormatter: DateTimeFormatter,
): ResumeDriver {
    val resumeBuilder = LatexSoberResumeBuilder(
        template,
        contentPlaceholder,
        workDateFormatter,
        educationDateFormatter,
    )
    return makeDriver(resumeBuilder) { resume ->
        resume.escapeLatex()
    }
}
