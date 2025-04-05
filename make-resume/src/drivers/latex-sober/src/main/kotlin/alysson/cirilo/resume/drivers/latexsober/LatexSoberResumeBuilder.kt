package alysson.cirilo.resume.drivers.latexsober

import alysson.cirilo.resume.drivers.utils.ResumeBuilder
import alysson.cirilo.resume.drivers.utils.indent.reindent
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.ProjectOrPublication
import java.time.format.DateTimeFormatter

internal class LatexSoberResumeBuilder(
    private val template: String,
    private val contentPlaceholder: String,
    workDateFormatter: DateTimeFormatter,
    educationDateFormatter: DateTimeFormatter,
) : ResumeBuilder {

    private val functionalSyntaxFactory = LatexSoberFunctionalSyntaxFactory(
        workDateFormatter,
        educationDateFormatter,
    )
    private var output = ""
    private var currentIndent = 0
    private var sectionIndent: Int? = null

    private fun updateOutput(newContent: String) {
        val separator = if (output.isEmpty()) "" else "\n"
        output += separator + newContent
    }

    override fun addHeader(
        name: String,
        headline: List<String>,
        contactInformation: ContactInformation,
    ): ResumeBuilder {
        updateOutput(
            functionalSyntaxFactory.makeHeader(name, headline, contactInformation).reindent(currentIndent),
        )
        return this
    }

    override fun startSection(name: String): ResumeBuilder {
        if (sectionIndent == null) {
            sectionIndent = currentIndent
        }
        sectionIndent?.let { theSectionIndent ->
            updateOutput(
                "\n" + functionalSyntaxFactory.makeSection(name).reindent(theSectionIndent),
            )
            currentIndent = theSectionIndent.inc()
        }
        return this
    }

    override fun makeExperiences(jobExperiences: List<JobExperience>): ResumeBuilder {
        functionalSyntaxFactory.makeExperiences(jobExperiences)?.let {
            updateOutput(it.reindent(currentIndent))
        }
        return this
    }

    override fun makeProjectsAndPublications(projectsAndPublications: List<ProjectOrPublication>): ResumeBuilder {
        functionalSyntaxFactory.makeProjectsAndPublications(projectsAndPublications)?.let { itemizedProjectAnPubs ->
            updateOutput(itemizedProjectAnPubs.reindent(currentIndent))
        }
        return this
    }

    override fun makeEducation(education: List<Degree>): ResumeBuilder {
        functionalSyntaxFactory.makeEducation(education)?.let { itemizedDegrees ->
            updateOutput(itemizedDegrees.reindent(currentIndent))
        }
        return this
    }

    override fun build(): String {
        return template.replace(contentPlaceholder, output.reindent(1)) + "\n"
    }
}
