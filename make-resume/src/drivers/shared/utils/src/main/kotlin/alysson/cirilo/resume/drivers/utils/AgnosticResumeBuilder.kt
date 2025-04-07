package alysson.cirilo.resume.drivers.utils

import alysson.cirilo.resume.entities.Resume

internal class AgnosticResumeBuilder(
    private val theResume: Resume,
    private val resumeBuilder: ResumeBuilder,
) {

    fun makeHeader(): AgnosticResumeBuilder {
        return new(resumeBuilder.addHeader(theResume.name, theResume.headline, theResume.contactInformation))
    }

    fun makeExperiences(): AgnosticResumeBuilder {
        return new(
            resumeBuilder.startSection("Experience")
                .makeExperiences(theResume.jobExperiences),
        )
    }

    fun makeProjectsAndPublications(): AgnosticResumeBuilder {
        return new(
            resumeBuilder.startSection("Projects & Publications")
                .makeProjectsAndPublications(theResume.projectsAndPublications),
        )
    }

    fun makeEducation(): AgnosticResumeBuilder {
        return new(
            resumeBuilder.startSection("Education")
                .makeEducation(theResume.education),
        )
    }

    fun build(): String {
        return resumeBuilder.build()
    }

    private fun new(builder: ResumeBuilder): AgnosticResumeBuilder {
        return AgnosticResumeBuilder(theResume, builder)
    }
}
