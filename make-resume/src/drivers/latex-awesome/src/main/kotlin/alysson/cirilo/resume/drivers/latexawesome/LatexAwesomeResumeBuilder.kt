package alysson.cirilo.resume.drivers.latexawesome

import alysson.cirilo.resume.drivers.utils.ResumeBuilder
import alysson.cirilo.resume.drivers.utils.indent.reindent
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.ProjectOrPublication
import java.time.format.DateTimeFormatter

internal class LatexAwesomeResumeBuilder(
    private val template: String,
    private val headerPlaceholder: String,
    private val contentPlaceholder: String,
    workDateFormatter: DateTimeFormatter,
    educationDateFormatter: DateTimeFormatter,
) : ResumeBuilder {

    private val functionalSyntaxFactory = LatexAwesomeFunctionalSyntaxFactory(
        workDateFormatter,
        educationDateFormatter,
    )
    private var output = ""
    private var header: String? = null
    private var currentIndent = 0
    private var sectionIndent: Int? = null

    private fun updateOutput(newContent: String) {
        output += if (output.isNotEmpty()) {
            "\n" + newContent
        } else {
            newContent
        }
    }

    override fun addHeader(
        name: String,
        headline: List<String>,
        contactInformation: ContactInformation,
    ): ResumeBuilder {
        header = functionalSyntaxFactory.makeHeader(name, headline, contactInformation)
        return this
    }

    override fun startSection(name: String): ResumeBuilder {
        if (sectionIndent == null) {
            sectionIndent = currentIndent
        }
        sectionIndent?.let { theSectionIndent ->
            val separator = if (output.isEmpty()) "" else "\n"
            updateOutput(separator + functionalSyntaxFactory.makeSection(name).reindent(theSectionIndent))
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
        val projectsAndPublicationsStr = functionalSyntaxFactory.makeProjectsAndPublications(projectsAndPublications)
        updateOutput(
            projectsAndPublicationsStr.reindent(currentIndent),
        )
        return this
    }

    override fun makeEducation(education: List<Degree>): ResumeBuilder {
        updateOutput(functionalSyntaxFactory.makeEducation(education).reindent(currentIndent))
        return this
    }

    override fun build(): String {
        val makeHeader = if (header != null) functionalSyntaxFactory.makeMakeHeaderCmd() else null
        val actualOutput = listOfNotNull(makeHeader, output).joinToString("\n\n")
        return template
            .replace(headerPlaceholder, header.orEmpty())
            .replace(contentPlaceholder, actualOutput.reindent(1)) +
            "\n"
    }
}
