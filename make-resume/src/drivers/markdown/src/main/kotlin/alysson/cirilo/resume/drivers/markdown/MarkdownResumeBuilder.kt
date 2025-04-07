package alysson.cirilo.resume.drivers.markdown

import alysson.cirilo.resume.drivers.utils.ResumeBuilder
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.ProjectOrPublication
import java.time.format.DateTimeFormatter

internal class MarkdownResumeBuilder(
    workDateFormatter: DateTimeFormatter,
    educationDateFormatter: DateTimeFormatter,
) : ResumeBuilder {

    private val syntaxFactory = MarkdownSyntaxFactory(
        workDateFormatter,
        educationDateFormatter,
    )
    private var output = ""

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
            syntaxFactory.makeHeader(name, headline, contactInformation),
        )
        return this
    }

    override fun startSection(name: String): ResumeBuilder {
        val extraLine = if (output.isEmpty()) "" else "\n"
        updateOutput("$extraLine${syntaxFactory.makeSection(name)}")
        return this
    }

    override fun makeExperiences(jobExperiences: List<JobExperience>): ResumeBuilder {
        updateOutput(
            syntaxFactory.makeExperiences(jobExperiences),
        )
        return this
    }

    override fun makeProjectsAndPublications(projectsAndPublications: List<ProjectOrPublication>): ResumeBuilder {
        updateOutput(
            syntaxFactory.makeProjectsAndPublications(projectsAndPublications),
        )
        return this
    }

    override fun makeEducation(education: List<Degree>): ResumeBuilder {
        updateOutput(
            syntaxFactory.makeEducation(education),
        )
        return this
    }

    override fun build(): String {
        return output + "\n"
    }
}
