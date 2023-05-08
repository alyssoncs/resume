package alysson.cirilo.resume.drivers

import alysson.cirilo.resume.entities.Resume
import alysson.cirilo.resume.infra.ResumeDriver

class LatexSoberResumeDriver : ResumeDriver {
    override fun convert(resume: Resume): String {
        val theResume = resume.reversedChronologically
        return LatexSoberResumeBuilder(theResume)
            .makeHeader()
            .makeExperiences()
            .startProjectAndPublicationsSection()
            .makeProjectsAndPublications()
            .startEducationSection()
            .makeEducation()
            .build()
    }
}
