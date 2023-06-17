package alysson.cirilo.resume.drivers.latexawesome

import alysson.cirilo.resume.drivers.utils.indent.reindent
import alysson.cirilo.resume.drivers.utils.syntaxfactory.ResumeSyntaxFactory
import alysson.cirilo.resume.entities.BulletPoint
import alysson.cirilo.resume.entities.BulletPointContent
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.EnrollmentPeriod
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.ProjectOrPublication
import alysson.cirilo.resume.entities.Role
import java.time.format.DateTimeFormatter

class LatexAwesomeSyntaxFactory(
    private val template: String,
    private val headerPlaceholder: String,
    private val contentPlaceholder: String,
    private val workDateFormatter: DateTimeFormatter,
    private val educationDateFormatter: DateTimeFormatter,
) : ResumeSyntaxFactory {

    private var output = ""
    private var header: Header? = null
    private var currentIndent = 0
    private var sectionIndent: Int? = null

    data class Header(
        val firstName: String,
        val lastName: String,
        val headline: List<String>,
        val contactInformation: ContactInformation,
    )

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
    ) {
        val space = ' '
        header = Header(
            firstName = name.substringBefore(space),
            lastName = name.substringAfter(space),
            headline = headline,
            contactInformation = contactInformation,
        )
    }

    override fun startSection(name: String) {
        if (sectionIndent == null) {
            sectionIndent = currentIndent
        }
        sectionIndent?.let { theSectionIndent ->
            val separator = if (output.isEmpty()) "" else "\n"
            updateOutput(separator + "\\cvsection{${name.replace("&", "\\&")}}".reindent(theSectionIndent))
            currentIndent = theSectionIndent.inc()
        }
    }

    override fun makeExperiences(jobExperiences: List<JobExperience>) {
        makeJobExperiences(jobExperiences)?.let {
            updateOutput(it.reindent(currentIndent))
        }
    }

    override fun makeProjectsAndPublications(projectsAndPublications: List<ProjectOrPublication>) {
        val projectsAndPublicationsStr = if (projectsAndPublications.isEmpty())
            ""
        else
            """
            \vspace{-15pt}
            \begin{cventries}
                \cventry
                    {}
                    {}
                    {}
                    {}
                    {
            """.trimIndent() + "\n" +
                itemize(projectsAndPublications.map(::makeProjectOrPublication)).reindent(3) + "\n" +
                """
                        }
                \end{cventries}
                """.trimIndent()

        updateOutput(
            projectsAndPublicationsStr.reindent(currentIndent)
        )
    }

    override fun makeEducation(education: List<Degree>) {
        updateOutput(
            if (education.isEmpty()) ""
            else
                (
                    "\\begin{cventries}\n" +
                        education.joinToString("\n") { makeDegree(it) }.reindent(1) +
                        "\n\\end{cventries}"
                    ).reindent(currentIndent)
        )
    }

    override fun create(): String {
        val makeHeader = if (header != null) "\\makecvheader[C]" else null
        val actualOutput = listOfNotNull(makeHeader, output).joinToString("\n\n")
        return template
            .replace(headerPlaceholder, awesomeHeader())
            .replace(contentPlaceholder, actualOutput.reindent(1)) +
            "\n"
    }

    private fun awesomeHeader(): String {
        return header?.let { safeHeader ->
            return """
            \name{${safeHeader.firstName}}{${safeHeader.lastName}}
            \position{${safeHeader.headline.joinToString("{\\enskip\\starredbullet\\enskip}")}}
            \address{${safeHeader.contactInformation.location.displayName}}
            \email{${safeHeader.contactInformation.email.displayName}}
            \github{${safeHeader.contactInformation.github.displayName}}
            \linkedin{${safeHeader.contactInformation.linkedin.displayName}}
            """.trimIndent()
        }.orEmpty()
    }

    private fun makeJobExperiences(jobExperiences: List<JobExperience>): String? {
        return if (jobExperiences.isEmpty())
            null
        else
            "\\begin{cventries}" + "\n" +
                jobExperiences.joinToString("\n\n") { makeJobExperience(it) }.reindent(1) +
                "\n\\end{cventries}"
    }

    private fun makeJobExperience(jobExperience: JobExperience): String {
        return listOfNotNull(
            makeFirstRole(jobExperience),
            makeOtherRoles(jobExperience),
        ).joinToString("\n\n")
    }

    private fun makeFirstRole(jobExperience: JobExperience): String {
        return makeARole(jobExperience, jobExperience.roles.first(), true)
    }

    private fun makeOtherRoles(jobExperience: JobExperience): String? {
        return if (jobExperience.roles.size == 1) {
            null
        } else {
            jobExperience.roles.drop(1).joinToString("\n\n") { role ->
                makeARole(jobExperience, role, false)
            }
        }
    }

    private fun makeARole(jobExperience: JobExperience, role: Role, isFirstRole: Boolean): String {
        return """
            \cventry
                {${role.title}}
                ${if (isFirstRole) "{\\iconhref{${jobExperience.company.url}}{${jobExperience.company.displayName}}}" else "{}"}
                ${if (isFirstRole) "{${jobExperience.location}}" else "{}"}
                {${makeWorkPeriod(role.period)}}
        """.trimIndent() +
            "\n" +
            if (role.bulletPoints.isEmpty()) {
                "{}".reindent(1)
            } else {
                "{".reindent(1) +
                    "\n" +
                    makeBulletPoints(role.bulletPoints).reindent(2) +
                    "\n" +
                    "}".reindent(1)
            }
    }

    private fun makeWorkPeriod(enrollmentPeriod: EnrollmentPeriod): String {
        return "${workDateFormatter.format(enrollmentPeriod.start)} -- ${
            makeEndDate(
                workDateFormatter,
                enrollmentPeriod.end
            )
        }"
    }

    private fun makeEndDate(
        formatter: DateTimeFormatter,
        endDate: EnrollmentPeriod.EndDate,
    ): String {
        return when (endDate) {
            is EnrollmentPeriod.EndDate.Past -> formatter.format(endDate.date)
            EnrollmentPeriod.EndDate.Present -> "Present"
        }
    }

    private fun makeBulletPoints(bulletPoints: List<BulletPoint>): String {
        if (bulletPoints.isEmpty()) return ""

        return itemize(bulletPoints.map(::makeBulletPoint))
    }

    private fun makeBulletPoint(bulletPoints: BulletPoint): String {
        return bulletPoints.content.joinToString(separator = "") {
            when (it) {
                is BulletPointContent.PlainText -> it.displayName
                is BulletPointContent.Skill -> "\\textbf{${it.displayName}}"
            }
        }
    }

    private fun itemize(items: List<String>): String {
        return "\\begin{cvitems}\n${
            items.joinToString("\n") { "\\item $it" }.reindent(1)
        }\n\\end{cvitems}"
    }

    private fun makeProjectOrPublication(projectOrPublication: ProjectOrPublication): String {
        return "\\textbf{\\iconhref{${projectOrPublication.title.url}}" +
            "{${projectOrPublication.title.displayName}}:} ${projectOrPublication.description}"
    }

    private fun makeDegree(degree: Degree): String {
        return """
            \cventry
                {${degree.degree}}
                {\iconhref{${degree.institution.url}}{${degree.institution.displayName}}}
                {${degree.location}}
                {${makeEduPeriod(degree.period)}}
                {}
        """.trimIndent()
    }

    private fun makeEduPeriod(enrollmentPeriod: EnrollmentPeriod): String {
        return "${educationDateFormatter.format(enrollmentPeriod.start)} -- ${
            makeEndDate(
                educationDateFormatter,
                enrollmentPeriod.end
            )
        }"
    }
}
