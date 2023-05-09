package alysson.cirilo.resume.drivers

import alysson.cirilo.resume.entities.Resume
import alysson.cirilo.resume.infra.ResumeDriver

class LatexAwesomeResumeDriver : ResumeDriver {
    override fun convert(resume: Resume): String {
        val theResume = resume.reversedChronologically
        return LatexAwesomeResumeBuilder(theResume)
            .makeHeader()
            .makeExperiences()
            .makeProjectsAndPublications()
            .makeEducation()
            .build()
    }
}
