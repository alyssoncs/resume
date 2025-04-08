package alysson.cirilo.resume.drivers.markdown

import alysson.cirilo.resume.drivers.utils.ResumeBuilder
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.ProjectOrPublication
import java.time.format.DateTimeFormatter

internal class MarkdownResumeBuilder private constructor(
    private val workDateFormatter: DateTimeFormatter,
    private val educationDateFormatter: DateTimeFormatter,
    private val output: String,
) : ResumeBuilder {

    constructor(
        workDateFormatter: DateTimeFormatter,
        educationDateFormatter: DateTimeFormatter,
    ) : this(workDateFormatter, educationDateFormatter, output = "")

    private val syntaxFactory = MarkdownSyntaxFactory(
        workDateFormatter,
        educationDateFormatter,
    )

    override fun addHeader(
        name: String,
        headline: List<String>,
        contactInformation: ContactInformation,
    ): ResumeBuilder {
        return new(syntaxFactory.makeHeader(name, headline, contactInformation))
    }

    override fun startSection(name: String): ResumeBuilder {
        val extraLine = if (output.isEmpty()) "" else "\n"
        return new("$extraLine${syntaxFactory.makeSection(name)}")
    }

    override fun makeExperiences(jobExperiences: List<JobExperience>): ResumeBuilder {
        return new(syntaxFactory.makeExperiences(jobExperiences))
    }

    override fun makeProjectsAndPublications(projectsAndPublications: List<ProjectOrPublication>): ResumeBuilder {
        return new(syntaxFactory.makeProjectsAndPublications(projectsAndPublications))
    }

    override fun makeEducation(education: List<Degree>): ResumeBuilder {
        return new(syntaxFactory.makeEducation(education))
    }

    override fun build(): String {
        return output + "\n"
    }

    private fun new(newContent: String): MarkdownResumeBuilder {
        return MarkdownResumeBuilder(
            workDateFormatter,
            educationDateFormatter,
            updateOutput(newContent),
        )
    }

    private fun updateOutput(newContent: String): String {
        val separator = if (output.isEmpty()) "" else "\n"
        val newOutput = output + separator + newContent
        return newOutput
    }
}
