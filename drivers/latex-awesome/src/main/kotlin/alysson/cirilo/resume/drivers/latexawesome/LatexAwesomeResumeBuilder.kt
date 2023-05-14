package alysson.cirilo.resume.drivers.latexawesome

import alysson.cirilo.resume.entities.Resume

class LatexAwesomeResumeBuilder(private val theResume: Resume) {

    private val latexAwesomeSyntaxFactory by lazy {
        fun String.asPlaceholder() = "%%${this}%%"
        LatexAwesomeSyntaxFactory(
            template = javaClass.getResource("/latex-awesome-resume-template.tex")!!.readText(),
            headerPlaceholder = "header".asPlaceholder(),
            contentPlaceholder = "content-goes-here".asPlaceholder()
        )
    }

    fun makeHeader(): LatexAwesomeResumeBuilder {
        latexAwesomeSyntaxFactory.addHeader(theResume.name, theResume.headline, theResume.contactInformation)
        return this
    }

    fun makeExperiences(): LatexAwesomeResumeBuilder {
        latexAwesomeSyntaxFactory.startSection("Experience")
        latexAwesomeSyntaxFactory.makeExperiences(theResume.jobExperiences)
        return this
    }

    fun makeProjectsAndPublications(): LatexAwesomeResumeBuilder {
        latexAwesomeSyntaxFactory.startSection("Projects \\& Publications")
        latexAwesomeSyntaxFactory.makeProjectsAndPublications(theResume.projectsAndPublications)
        return this
    }

    fun makeEducation(): LatexAwesomeResumeBuilder {
        latexAwesomeSyntaxFactory.startSection("Education")
        latexAwesomeSyntaxFactory.makeEducation(theResume.education)
        return this
    }

    fun build(): String {
        return latexAwesomeSyntaxFactory.create()
    }
}
