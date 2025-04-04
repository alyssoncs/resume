package alysson.cirilo.resume.drivers.latexawesome

import alysson.cirilo.resume.drivers.utils.indent.reindent
import alysson.cirilo.resume.drivers.utils.syntaxfactory.ResumeSyntaxFactory
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.ProjectOrPublication
import java.time.format.DateTimeFormatter

internal class LatexAwesomeSyntaxFactory(
    private val template: String,
    private val headerPlaceholder: String,
    private val contentPlaceholder: String,
    workDateFormatter: DateTimeFormatter,
    educationDateFormatter: DateTimeFormatter,
) : ResumeSyntaxFactory {

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

    override fun addHeader(name: String, headline: List<String>, contactInformation: ContactInformation) {
        header = functionalSyntaxFactory.makeHeader(name, headline, contactInformation)
    }

    override fun startSection(name: String) {
        if (sectionIndent == null) {
            sectionIndent = currentIndent
        }
        sectionIndent?.let { theSectionIndent ->
            val separator = if (output.isEmpty()) "" else "\n"
            updateOutput(separator + functionalSyntaxFactory.makeSection(name).reindent(theSectionIndent))
            currentIndent = theSectionIndent.inc()
        }
    }

    override fun makeExperiences(jobExperiences: List<JobExperience>) {
        functionalSyntaxFactory.makeExperiences(jobExperiences)?.let {
            updateOutput(it.reindent(currentIndent))
        }
    }

    override fun makeProjectsAndPublications(projectsAndPublications: List<ProjectOrPublication>) {
        val projectsAndPublicationsStr = functionalSyntaxFactory.makeProjectsAndPublications(projectsAndPublications)
        updateOutput(
            projectsAndPublicationsStr.reindent(currentIndent),
        )
    }

    override fun makeEducation(education: List<Degree>) {
        updateOutput(functionalSyntaxFactory.makeEducation(education).reindent(currentIndent))
    }

    override fun create(): String {
        val makeHeader = if (header != null) functionalSyntaxFactory.makeMakeHeaderCmd() else null
        val actualOutput = listOfNotNull(makeHeader, output).joinToString("\n\n")
        return template
            .replace(headerPlaceholder, header.orEmpty())
            .replace(contentPlaceholder, actualOutput.reindent(1)) +
            "\n"
    }
}
