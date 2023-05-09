package alysson.cirilo.resume.drivers

import alysson.cirilo.resume.entities.Resume
import alysson.cirilo.resume.infra.ResumeDriver

class MarkdownResumeDriver : ResumeDriver {
    override fun convert(resume: Resume): String {
        val theResume = resume.reversedChronologically
        return MarkdownResumeBuilder(theResume)
            .makeHeader()
            .makeExperiences()
            .makeProjectsAndPublications()
            .makeEducation()
            .build()
    }
}
