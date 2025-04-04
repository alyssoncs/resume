package alysson.cirilo.resume.drivers.latexsober

import alysson.cirilo.resume.drivers.utils.indent.reindent
import alysson.cirilo.resume.drivers.utils.syntaxfactory.ResumeSyntaxFactory
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.ProjectOrPublication
import java.time.format.DateTimeFormatter

internal class LatexSoberSyntaxFactory(
    private val template: String,
    private val contentPlaceholder: String,
    workDateFormatter: DateTimeFormatter,
    educationDateFormatter: DateTimeFormatter,
) : ResumeSyntaxFactory {

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

    override fun addHeader(name: String, headline: List<String>, contactInformation: ContactInformation) {
        updateOutput(
            functionalSyntaxFactory.makeHeader(name, headline, contactInformation).reindent(currentIndent),
        )
    }

    override fun startSection(name: String) {
        if (sectionIndent == null) {
            sectionIndent = currentIndent
        }
        sectionIndent?.let { theSectionIndent ->
            updateOutput(
                "\n" + functionalSyntaxFactory.makeSection(name).reindent(theSectionIndent),
            )
            currentIndent = theSectionIndent.inc()
        }
    }

    override fun makeExperiences(jobExperiences: List<JobExperience>) {
        functionalSyntaxFactory.makeExperiences(jobExperiences)?.let {
            updateOutput(it.reindent(currentIndent))
        }
    }

    override fun makeProjectsAndPublications(projectsAndPublications: List<ProjectOrPublication>) {
        functionalSyntaxFactory.makeProjectsAndPublications(projectsAndPublications)?.let { itemizedProjectAnPubs ->
            updateOutput(itemizedProjectAnPubs.reindent(currentIndent))
        }
    }

    override fun makeEducation(education: List<Degree>) {
        functionalSyntaxFactory.makeEducation(education)?.let { itemizedDegrees ->
            updateOutput(itemizedDegrees.reindent(currentIndent))
        }
    }

    override fun create(): String {
        return template.replace(contentPlaceholder, output.reindent(1)) + "\n"
    }
}
