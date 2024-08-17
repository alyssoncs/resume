package alysson.cirilo.resume.drivers.utils

import alysson.cirilo.resume.drivers.utils.syntaxfactory.ResumeSyntaxFactory
import alysson.cirilo.resume.entities.Resume
import alysson.cirilo.resume.infra.ResumeDriver

fun makeDriver(
    syntaxFactory: ResumeSyntaxFactory,
    preProcess: (resume: Resume) -> Resume = { resume -> resume },
): ResumeDriver {
    return AgnosticResumeDriver(preProcess) { resume ->
        AgnosticResumeBuilder(resume, syntaxFactory)
    }
}
