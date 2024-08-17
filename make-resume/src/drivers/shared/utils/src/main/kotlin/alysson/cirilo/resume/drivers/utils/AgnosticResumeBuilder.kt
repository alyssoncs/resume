package alysson.cirilo.resume.drivers.utils

import alysson.cirilo.resume.drivers.utils.syntaxfactory.ResumeSyntaxFactory
import alysson.cirilo.resume.entities.Resume

internal class AgnosticResumeBuilder(
    private val theResume: Resume,
    private val resumeSyntaxFactory: ResumeSyntaxFactory,
) {

    fun makeHeader(): AgnosticResumeBuilder {
        this.resumeSyntaxFactory.addHeader(theResume.name, theResume.headline, theResume.contactInformation)
        return this
    }

    fun makeExperiences(): AgnosticResumeBuilder {
        this.resumeSyntaxFactory.startSection("Experience")
        this.resumeSyntaxFactory.makeExperiences(theResume.jobExperiences)
        return this
    }

    fun makeProjectsAndPublications(): AgnosticResumeBuilder {
        this.resumeSyntaxFactory.startSection("Projects & Publications")
        this.resumeSyntaxFactory.makeProjectsAndPublications(theResume.projectsAndPublications)
        return this
    }

    fun makeEducation(): AgnosticResumeBuilder {
        this.resumeSyntaxFactory.startSection("Education")
        this.resumeSyntaxFactory.makeEducation(theResume.education)
        return this
    }

    fun build(): String {
        return this.resumeSyntaxFactory.create()
    }
}
