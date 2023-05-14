package alysson.cirilo.resume.drivers

import alysson.cirilo.resume.entities.Resume

class LatexSoberResumeBuilder(private val theResume: Resume) {

    private val latexSoberSyntaxFactory by lazy {
        val template = javaClass.getResource("/latex-sober-resume-template.tex")!!.readText()
        LatexSoberSyntaxFactory(template, "%%content-goes-here%%")
    }

    fun makeHeader(): LatexSoberResumeBuilder {
        latexSoberSyntaxFactory.addHeader(theResume.name, theResume.headline, theResume.contactInformation)
        return this
    }

    fun makeExperiences(): LatexSoberResumeBuilder {
        latexSoberSyntaxFactory.startSection("Experience")
        latexSoberSyntaxFactory.makeExperiences(theResume.jobExperiences)
        return this
    }

    fun makeProjectsAndPublications(): LatexSoberResumeBuilder {
        latexSoberSyntaxFactory.startSection("Projects & Publications")
        latexSoberSyntaxFactory.makeProjectsAndPublications(theResume.projectsAndPublications)
        return this
    }

    fun makeEducation(): LatexSoberResumeBuilder {
        latexSoberSyntaxFactory.startSection("Education")
        latexSoberSyntaxFactory.makeEducation(theResume.education)
        return this
    }

    fun build(): String {
        return latexSoberSyntaxFactory.toString()
    }
}
