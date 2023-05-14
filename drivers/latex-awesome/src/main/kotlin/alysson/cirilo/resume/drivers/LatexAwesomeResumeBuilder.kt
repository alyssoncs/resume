package alysson.cirilo.resume.drivers

import alysson.cirilo.resume.entities.Resume

class LatexAwesomeResumeBuilder(private val theResume: Resume) {

    private val latexAwesomeSyntaxFactory by lazy {
        fun String.asPlaceholder() = "%%${this}%%"
        LatexAwesomeSyntaxFactory(
            resource = "/latex-awesome-resume-template.tex",
            firstNamePlaceholder = "first-name".asPlaceholder(),
            lastNamePlaceholder = "last-name".asPlaceholder(),
            headlinePlaceholder = "headline".asPlaceholder(),
            addressPlaceholder = "address".asPlaceholder(),
            emailPlaceholder = "email".asPlaceholder(),
            githubPlaceholder = "github".asPlaceholder(),
            linkedinPlaceholder = "linkedin".asPlaceholder(),
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
        return latexAwesomeSyntaxFactory.toString()
    }
}
