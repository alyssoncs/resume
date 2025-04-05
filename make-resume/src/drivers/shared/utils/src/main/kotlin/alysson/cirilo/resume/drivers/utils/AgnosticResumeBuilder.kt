package alysson.cirilo.resume.drivers.utils

import alysson.cirilo.resume.entities.Resume

internal class AgnosticResumeBuilder(
    private val theResume: Resume,
    private val resumeBuilder: ResumeBuilder,
) {

    fun makeHeader(): AgnosticResumeBuilder {
        this.resumeBuilder.addHeader(theResume.name, theResume.headline, theResume.contactInformation)
        return this
    }

    fun makeExperiences(): AgnosticResumeBuilder {
        this.resumeBuilder.startSection("Experience")
        this.resumeBuilder.makeExperiences(theResume.jobExperiences)
        return this
    }

    fun makeProjectsAndPublications(): AgnosticResumeBuilder {
        this.resumeBuilder.startSection("Projects & Publications")
        this.resumeBuilder.makeProjectsAndPublications(theResume.projectsAndPublications)
        return this
    }

    fun makeEducation(): AgnosticResumeBuilder {
        this.resumeBuilder.startSection("Education")
        this.resumeBuilder.makeEducation(theResume.education)
        return this
    }

    fun build(): String {
        return this.resumeBuilder.build()
    }
}
