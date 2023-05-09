package alysson.cirilo.resume.drivers

import alysson.cirilo.resume.entities.Resume

class MarkdownResumeBuilder(private val theResume: Resume) {

    private val markdownResume by lazy {
        MarkdownResume()
    }

    fun makeHeader(): MarkdownResumeBuilder {
        markdownResume.addHeader(theResume.name, theResume.headline, theResume.contactInformation)
        return this
    }

    fun makeExperiences(): MarkdownResumeBuilder {
        markdownResume.startSection("Experience")
        markdownResume.makeExperiences(theResume.jobExperiences)
        return this
    }

    fun makeProjectsAndPublications(): MarkdownResumeBuilder {
        markdownResume.startSection("Projects & Publications")
        markdownResume.makeProjectsAndPublications(theResume.projectsAndPublications)
        return this
    }

    fun makeEducation(): MarkdownResumeBuilder {
        markdownResume.startSection("Education")
        markdownResume.makeEducation(theResume.education)
        return this
    }

    fun build(): String {
        return markdownResume.toString()
    }
}
