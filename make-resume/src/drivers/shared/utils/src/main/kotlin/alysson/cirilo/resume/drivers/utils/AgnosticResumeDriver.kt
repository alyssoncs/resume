package alysson.cirilo.resume.drivers.utils

import alysson.cirilo.resume.entities.Resume
import alysson.cirilo.resume.infra.ResumeDriver

internal class AgnosticResumeDriver(
    private val preProcess: (resume: Resume) -> Resume,
    private val resumeBuilder: ResumeBuilder,
) : ResumeDriver {
    override fun convert(resume: Resume): String {
        val theResume = preProcess(resume.reversedChronologically)
        return resumeBuilder
            .addHeader(theResume.name, theResume.headline, theResume.contactInformation)
            .startSection("Experience")
            .makeExperiences(theResume.jobExperiences)
            .startSection("Projects & Publications")
            .makeProjectsAndPublications(theResume.projectsAndPublications)
            .startSection("Education")
            .makeEducation(theResume.education)
            .build()
    }
}
