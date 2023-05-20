package alysson.cirilo.resume.drivers.utils

import alysson.cirilo.resume.drivers.utils.syntaxfactory.ResumeSyntaxFactory
import alysson.cirilo.resume.infra.ResumeDriver

fun makeAgnosticDriver(
    syntaxFactory: ResumeSyntaxFactory,
): ResumeDriver {
    return AgnosticResumeDriver { resume ->
        AgnosticResumeBuilder(resume, syntaxFactory)
    }
}
