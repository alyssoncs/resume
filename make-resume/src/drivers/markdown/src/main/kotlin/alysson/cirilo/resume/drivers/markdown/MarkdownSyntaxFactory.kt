package alysson.cirilo.resume.drivers.markdown

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

internal class MarkdownSyntaxFactory(
    private val workDateFormatter: DateTimeFormatter,
    private val educationDateFormatter: DateTimeFormatter,
) : ResumeSyntaxFactory {

    private var output = ""

    private fun updateOutput(newContent: String) {
        val separator = if (output.isEmpty()) "" else "\n"
        output += separator + newContent
    }

    override fun addHeader(name: String, headline: List<String>, contactInformation: ContactInformation) {
        updateOutput(
            """
                # $name
                > ${headline.joinToString(" • ")}
                
                ## Contact Information
                - [${contactInformation.email.displayName}](${contactInformation.email.url})
                - [${contactInformation.homepage.displayName}](${contactInformation.homepage.url})
                - [${contactInformation.linkedin.displayName}](${contactInformation.linkedin.url})
                - [${contactInformation.github.displayName}](${contactInformation.github.url})
                - [${contactInformation.location.displayName}](${contactInformation.location.url})
            """.trimIndent(),
        )
    }

    override fun startSection(name: String) {
        val extraLine = if (output.isEmpty()) "" else "\n"
        updateOutput("$extraLine## $name")
    }

    override fun makeExperiences(jobExperiences: List<JobExperience>) {
        updateOutput(
            makeJobExperiences(jobExperiences),
        )
    }

    override fun makeProjectsAndPublications(projectsAndPublications: List<ProjectOrPublication>) {
        val projectsAndPublicationsStr = if (projectsAndPublications.isEmpty())
            ""
        else
            itemize(projectsAndPublications.map(::makeProjectOrPublication))

        updateOutput(
            projectsAndPublicationsStr.trimIndent(),
        )
    }

    override fun makeEducation(education: List<Degree>) {
        updateOutput(
            (if (education.isEmpty()) "" else itemize(education.map(::makeDegree))).trimIndent(),
        )
    }

    override fun create(): String {
        return output + "\n"
    }

    private fun makeJobExperiences(jobExperiences: List<JobExperience>): String {
        return jobExperiences.joinToString("\n\n") { makeJobExperience(it) }
    }

    private fun makeJobExperience(jobExperience: JobExperience): String {
        return """
            ### [${jobExperience.company.displayName}](${jobExperience.company.url})
            - ${jobExperience.location}
        """.trimIndent() + "\n\n" +
            jobExperience.roles.joinToString(separator = "\n\n") { makeRole(it) }
    }

    private fun makeRole(role: Role): String {
        return listOfNotNull(
            makeRoleHeader(role),
            makeBulletPoints(role.bulletPoints),
        ).joinToString("\n")
    }

    private fun makeRoleHeader(role: Role): String {
        return """
            #### ${role.title}
            > ${makeWorkPeriod(role.period)}
        """.trimIndent()
    }

    private fun makeWorkPeriod(enrollmentPeriod: EnrollmentPeriod): String {
        return "${workDateFormatter.format(enrollmentPeriod.start)} – ${
            makeEndDate(workDateFormatter, enrollmentPeriod.end)
        }"
    }

    private fun makeEndDate(formatter: DateTimeFormatter, endDate: EnrollmentPeriod.EndDate): String {
        return when (endDate) {
            is EnrollmentPeriod.EndDate.Past -> formatter.format(endDate.date)
            EnrollmentPeriod.EndDate.Present -> "Present"
        }
    }

    private fun makeBulletPoints(bulletPoints: List<BulletPoint>): String? {
        if (bulletPoints.isEmpty()) return null

        return itemize(bulletPoints.map(::makeBulletPointContent))
    }

    private fun makeBulletPointContent(bulletPoint: BulletPoint): String {
        return bulletPoint.content.joinToString(separator = "") {
            when (it) {
                is BulletPointContent.PlainText -> it.displayName
                is BulletPointContent.Skill -> "**${it.displayName}**"
            }
        }
    }

    private fun itemize(items: List<String>): String {
        return items.joinToString("\n") { "- $it" }
    }

    private fun makeProjectOrPublication(projectOrPublication: ProjectOrPublication): String {
        return """
                **[${projectOrPublication.title.displayName}](${projectOrPublication.title.url})**: ${projectOrPublication.description}
        """.trimIndent()
    }

    private fun makeDegree(degree: Degree): String {
        return """
                **[${degree.institution.displayName}](${degree.institution.url})**: ${degree.degree} (${degree.location} ${
            makeEduPeriod(
                degree.period,
            )
        })
        """.trimIndent()
    }

    private fun makeEduPeriod(enrollmentPeriod: EnrollmentPeriod): String {
        return "${educationDateFormatter.format(enrollmentPeriod.start)} – ${
            makeEndDate(
                educationDateFormatter,
                enrollmentPeriod.end,
            )
        }"
    }
}
