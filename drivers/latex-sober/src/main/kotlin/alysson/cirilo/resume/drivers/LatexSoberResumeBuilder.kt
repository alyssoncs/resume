package alysson.cirilo.resume.drivers

import alysson.cirilo.resume.entities.Resume

class LatexSoberResumeBuilder(private val theResume: Resume) {

    private val latexSoberResume by lazy {
        LatexSoberResume("/latex-sober-resume-template.tex", "%%content-goes-here%%")
    }

    fun makeHeader(): LatexSoberResumeBuilder {
        latexSoberResume.addHeader(theResume.name, theResume.headline, theResume.contactInformation)
        return this
    }

    fun makeExperiences(): LatexSoberResumeBuilder {
        latexSoberResume.startSection("Experience")
        latexSoberResume.makeExperiences(theResume.jobExperiences)
        return this
    }

    fun makeProjectsAndPublications(): LatexSoberResumeBuilder {
        latexSoberResume.startSection("Projects \\textit{\\&} Publications")
        latexSoberResume.makeProjectsAndPublications(theResume.projectsAndPublications)
        return this
    }

    fun makeEducation(): LatexSoberResumeBuilder {
        latexSoberResume.startSection("Education")
        latexSoberResume.makeEducation(theResume.education)
        return this
    }

    fun build(): String {
        return latexSoberResume.toString()
    }
}
