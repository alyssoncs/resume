package alysson.cirilo.resume.drivers.utils

import alysson.cirilo.resume.entities.Resume
import alysson.cirilo.resume.infra.ResumeDriver

internal class AgnosticResumeDriver(
    private val getResumeBuilder: (resume: Resume) -> AgnosticResumeBuilder,
) : ResumeDriver {
    override fun convert(resume: Resume): String {
        val theResume = resume.reversedChronologically
        return getResumeBuilder(theResume)
            .makeHeader()
            .makeExperiences()
            .makeProjectsAndPublications()
            .makeEducation()
            .build()
    }
}
