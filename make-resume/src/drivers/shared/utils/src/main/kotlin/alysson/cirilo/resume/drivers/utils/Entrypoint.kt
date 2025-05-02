package alysson.cirilo.resume.drivers.utils

import alysson.cirilo.resume.entities.Resume
import alysson.cirilo.resume.infra.ResumeDriver

fun makeDriver(
    resumeBuilder: ResumeBuilder,
    preProcess: (resume: Resume) -> Resume = { resume -> resume },
): ResumeDriver {
    return AgnosticResumeDriver(preProcess, resumeBuilder)
}
