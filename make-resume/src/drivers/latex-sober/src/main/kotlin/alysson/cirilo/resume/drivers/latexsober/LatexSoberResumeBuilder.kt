package alysson.cirilo.resume.drivers.latexsober

import alysson.cirilo.resume.drivers.utils.ResumeBuilder
import alysson.cirilo.resume.drivers.utils.indent.reindent
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.ProjectOrPublication
import java.time.format.DateTimeFormatter

internal class LatexSoberResumeBuilder private constructor(
    private val state: State = State(),
    private val template: String,
    private val contentPlaceholder: String,
    private val workDateFormatter: DateTimeFormatter,
    private val educationDateFormatter: DateTimeFormatter,
) : ResumeBuilder {

    constructor(
        template: String,
        contentPlaceholder: String,
        workDateFormatter: DateTimeFormatter,
        educationDateFormatter: DateTimeFormatter,
    ) : this(
        state = State(),
        template = template,
        contentPlaceholder = contentPlaceholder,
        workDateFormatter = workDateFormatter,
        educationDateFormatter = educationDateFormatter,
    )

    private val syntaxFactory = LatexSoberSyntaxFactory(
        workDateFormatter,
        educationDateFormatter,
    )

    private data class State(
        val output: String = "",
        val sectionStarted: Boolean = false,
    ) {
        val sectionIndent: Int = 0
        val currentIndent: Int = if (sectionStarted) 1 else 0
    }

    override fun addHeader(
        name: String,
        headline: List<String>,
        contactInformation: ContactInformation,
    ): ResumeBuilder {
        return new(
            syntaxFactory.makeHeader(name, headline, contactInformation).reindent(state.currentIndent),
        )
    }

    override fun startSection(name: String): ResumeBuilder {
        return new(
            newContent = "\n" + syntaxFactory.makeSection(name).reindent(state.sectionIndent),
            isSection = true,
        )
    }

    override fun makeExperiences(jobExperiences: List<JobExperience>): ResumeBuilder {
        syntaxFactory.makeExperiences(jobExperiences)?.let {
            return new(it.reindent(state.currentIndent))
        }
        return this
    }

    override fun makeProjectsAndPublications(projectsAndPublications: List<ProjectOrPublication>): ResumeBuilder {
        syntaxFactory.makeProjectsAndPublications(projectsAndPublications)?.let { itemizedProjectAnPubs ->
            return new(itemizedProjectAnPubs.reindent(state.currentIndent))
        }
        return this
    }

    override fun makeEducation(education: List<Degree>): ResumeBuilder {
        syntaxFactory.makeEducation(education)?.let { itemizedDegrees ->
            return new(itemizedDegrees.reindent(state.currentIndent))
        }
        return this
    }

    override fun build(): String {
        return template.replace(contentPlaceholder, state.output.reindent(1)) + "\n"
    }

    private fun new(newContent: String, isSection: Boolean = false): LatexSoberResumeBuilder {
        return LatexSoberResumeBuilder(
            state = updateState(newContent, isSection),
            template = template,
            contentPlaceholder = contentPlaceholder,
            workDateFormatter = workDateFormatter,
            educationDateFormatter = educationDateFormatter,
        )
    }

    private fun updateState(newContent: String, isSection: Boolean): State {
        val separator = if (state.output.isEmpty()) "" else "\n"
        val newState = state.copy(output = state.output + separator + newContent, sectionStarted = isSection)
        return newState
    }
}
