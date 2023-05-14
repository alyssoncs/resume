package alysson.cirilo.resume.drivers

import alysson.cirilo.resume.entities.Resume

class MarkdownResumeBuilder(private val theResume: Resume) {

    private val markdownSyntaxFactory by lazy {
        MarkdownSyntaxFactory()
    }

    fun makeHeader(): MarkdownResumeBuilder {
        markdownSyntaxFactory.addHeader(theResume.name, theResume.headline, theResume.contactInformation)
        return this
    }

    fun makeExperiences(): MarkdownResumeBuilder {
        markdownSyntaxFactory.startSection("Experience")
        markdownSyntaxFactory.makeExperiences(theResume.jobExperiences)
        return this
    }

    fun makeProjectsAndPublications(): MarkdownResumeBuilder {
        markdownSyntaxFactory.startSection("Projects & Publications")
        markdownSyntaxFactory.makeProjectsAndPublications(theResume.projectsAndPublications)
        return this
    }

    fun makeEducation(): MarkdownResumeBuilder {
        markdownSyntaxFactory.startSection("Education")
        markdownSyntaxFactory.makeEducation(theResume.education)
        return this
    }

    fun build(): String {
        return markdownSyntaxFactory.create()
    }
}
