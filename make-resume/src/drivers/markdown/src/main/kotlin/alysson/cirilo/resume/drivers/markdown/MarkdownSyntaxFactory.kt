package alysson.cirilo.resume.drivers.markdown

import alysson.cirilo.resume.drivers.utils.syntaxfactory.ResumeSyntaxFactory
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.ProjectOrPublication
import java.time.format.DateTimeFormatter

internal class MarkdownSyntaxFactory(
    workDateFormatter: DateTimeFormatter,
    educationDateFormatter: DateTimeFormatter,
) : ResumeSyntaxFactory {

    private val functionalSyntaxFactory = MarkdownFunctionalSyntaxFactory(
        workDateFormatter,
        educationDateFormatter,
    )
    private var output = ""

    private fun updateOutput(newContent: String) {
        val separator = if (output.isEmpty()) "" else "\n"
        output += separator + newContent
    }

    override fun addHeader(name: String, headline: List<String>, contactInformation: ContactInformation) {
        updateOutput(
            functionalSyntaxFactory.makeHeader(name, headline, contactInformation),
        )
    }

    override fun startSection(name: String) {
        val extraLine = if (output.isEmpty()) "" else "\n"
        updateOutput("$extraLine${functionalSyntaxFactory.makeSection(name)}")
    }

    override fun makeExperiences(jobExperiences: List<JobExperience>) {
        updateOutput(
            functionalSyntaxFactory.makeExperiences(jobExperiences),
        )
    }

    override fun makeProjectsAndPublications(projectsAndPublications: List<ProjectOrPublication>) {
        updateOutput(
            functionalSyntaxFactory.makeProjectsAndPublications(projectsAndPublications),
        )
    }

    override fun makeEducation(education: List<Degree>) {
        updateOutput(
            functionalSyntaxFactory.makeEducation(education),
        )
    }

    override fun create(): String {
        return output + "\n"
    }
}
