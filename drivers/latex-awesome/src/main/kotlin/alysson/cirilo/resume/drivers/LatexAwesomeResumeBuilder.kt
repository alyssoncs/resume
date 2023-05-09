package alysson.cirilo.resume.drivers

import alysson.cirilo.resume.entities.Resume

class LatexAwesomeResumeBuilder(private val theResume: Resume) {

    private val latexSoberResume by lazy {
        fun String.asPlaceholder() = "%%${this}%%"
        LatexAwesomeResume(
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
        latexSoberResume.addHeader(theResume.name, theResume.headline, theResume.contactInformation)
        return this
    }

    fun makeExperiences(): LatexAwesomeResumeBuilder {
        latexSoberResume.startSection("Experience")
        latexSoberResume.makeExperiences(theResume.jobExperiences)
        return this
    }

    fun makeProjectsAndPublications(): LatexAwesomeResumeBuilder {
        latexSoberResume.startSection("Projects \\& Publications")
        latexSoberResume.makeProjectsAndPublications(theResume.projectsAndPublications)
        return this
    }

    fun makeEducation(): LatexAwesomeResumeBuilder {
        latexSoberResume.startSection("Education")
        latexSoberResume.makeEducation(theResume.education)
        return this
    }

    fun build(): String {
        return latexSoberResume.toString()
    }
}
