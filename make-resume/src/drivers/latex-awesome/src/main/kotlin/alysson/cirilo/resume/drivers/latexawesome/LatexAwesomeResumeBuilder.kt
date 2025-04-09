package alysson.cirilo.resume.drivers.latexawesome

import alysson.cirilo.resume.drivers.utils.ResumeBuilder
import alysson.cirilo.resume.drivers.utils.indent.reindent
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.ProjectOrPublication
import java.time.format.DateTimeFormatter

internal class LatexAwesomeResumeBuilder private constructor(
    private val state: State,
    private val template: String,
    private val headerPlaceholder: String,
    private val contentPlaceholder: String,
    private val workDateFormatter: DateTimeFormatter,
    private val educationDateFormatter: DateTimeFormatter,
) : ResumeBuilder {

    constructor(
        template: String,
        headerPlaceholder: String,
        contentPlaceholder: String,
        workDateFormatter: DateTimeFormatter,
        educationDateFormatter: DateTimeFormatter,
    ) : this(
        state = State(),
        template = template,
        headerPlaceholder = headerPlaceholder,
        contentPlaceholder = contentPlaceholder,
        workDateFormatter = workDateFormatter,
        educationDateFormatter = educationDateFormatter,
    )

    private val syntaxFactory = LatexAwesomeSyntaxFactory(
        workDateFormatter,
        educationDateFormatter,
    )

    private data class State(
        val output: String = "",
        val sectionStarted: Boolean = false,
        val header: String? = null,
    ) {
        val sectionIndent: Int = 0
        val currentIndent: Int = if (sectionStarted) 1 else 0
    }

    override fun addHeader(
        name: String,
        headline: List<String>,
        contactInformation: ContactInformation,
    ): ResumeBuilder {
        return header(header = syntaxFactory.makeHeader(name, headline, contactInformation))
    }

    override fun startSection(name: String): ResumeBuilder {
        val separator = if (state.output.isEmpty()) "" else "\n"
        return new(separator + syntaxFactory.makeSection(name).reindent(state.sectionIndent), isSection = true)
    }

    override fun makeExperiences(jobExperiences: List<JobExperience>): ResumeBuilder {
        syntaxFactory.makeExperiences(jobExperiences)?.let {
            return new(it.reindent(state.currentIndent))
        }
        return this
    }

    override fun makeProjectsAndPublications(projectsAndPublications: List<ProjectOrPublication>): ResumeBuilder {
        val projectsAndPublicationsStr = syntaxFactory.makeProjectsAndPublications(projectsAndPublications)
        return new(projectsAndPublicationsStr.reindent(state.currentIndent))
    }

    override fun makeEducation(education: List<Degree>): ResumeBuilder {
        return new(syntaxFactory.makeEducation(education).reindent(state.currentIndent))
    }

    override fun build(): String {
        val makeHeader = if (state.header != null) syntaxFactory.makeMakeHeaderCmd() else null
        val actualOutput = listOfNotNull(makeHeader, state.output).joinToString("\n\n")
        return template
            .replace(headerPlaceholder, state.header.orEmpty())
            .replace(contentPlaceholder, actualOutput.reindent(1)) +
            "\n"
    }

    private fun header(header: String): LatexAwesomeResumeBuilder {
        return LatexAwesomeResumeBuilder(
            state = state.copy(header = header),
            template = template,
            headerPlaceholder = headerPlaceholder,
            contentPlaceholder = contentPlaceholder,
            workDateFormatter = workDateFormatter,
            educationDateFormatter = educationDateFormatter,
        )
    }

    private fun new(newContent: String, isSection: Boolean = false): LatexAwesomeResumeBuilder {
        return LatexAwesomeResumeBuilder(
            state = updateState(newContent, isSection),
            template = template,
            headerPlaceholder = headerPlaceholder,
            contentPlaceholder = contentPlaceholder,
            workDateFormatter = workDateFormatter,
            educationDateFormatter = educationDateFormatter,
        )
    }

    private fun updateState(content: String, isSection: Boolean): State {
        val separator = if (state.output.isNotEmpty()) "\n" else ""
        val newState = state.copy(output = "${state.output}$separator$content", sectionStarted = isSection)
        return newState
    }
}
