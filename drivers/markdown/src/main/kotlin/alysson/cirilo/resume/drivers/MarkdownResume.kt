package alysson.cirilo.resume.drivers

import alysson.cirilo.resume.entities.BulletPoint
import alysson.cirilo.resume.entities.BulletPointContent
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.EnrollmentPeriod
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.ProjectOrPublication
import alysson.cirilo.resume.entities.Role
import java.time.format.DateTimeFormatter
import java.util.Locale

class MarkdownResume {

    private var output = ""

    private fun updateOutput(newContent: String) {
        output += newContent
    }

    fun addHeader(name: String, headline: List<String>, contactInformation: ContactInformation) {
        updateOutput(
            """
                # $name
                > ${headline.joinToString(" • ")}
                
                ## Contact information
                - [${contactInformation.email.displayName}](${contactInformation.email.url})
                - [${contactInformation.linkedin.displayName}](${contactInformation.linkedin.url})
                - [${contactInformation.github.displayName}](${contactInformation.github.url})
                - [${contactInformation.location.displayName}](${contactInformation.location.url})

            """.trimIndent()
        )
    }

    fun startSection(name: String) {
        updateOutput("\n## $name\n")
    }

    fun makeExperiences(jobExperiences: List<JobExperience>) {
        updateOutput(
            makeJobExperiences(jobExperiences).trimIndent() + "\n"
        )
    }

    fun makeProjectsAndPublications(projectsAndPublications: List<ProjectOrPublication>) {
        val projectsAndPublicationsStr = if (projectsAndPublications.isEmpty())
            ""
        else
            itemize(projectsAndPublications.map(::makeProjectOrPublication))

        updateOutput(
            projectsAndPublicationsStr.trimIndent() + "\n"
        )
    }

    fun makeEducation(education: List<Degree>) {
        updateOutput(
            (if (education.isEmpty()) ""
            else itemize(education.map(::makeDegree))).trimIndent() + "\n"
        )
    }

    override fun toString(): String {
        return output
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
        return """
            #### ${role.title}
            > ${makeWorkPeriod(role.period)}.
            """.trimIndent() + "\n" +
                makeBulletPoints(role.bulletPoints)
    }

    private fun makeWorkPeriod(enrollmentPeriod: EnrollmentPeriod): String {
        val formatter = DateTimeFormatter.ofPattern("MMM. yyyy").withLocale(Locale.US)

        return "${formatter.format(enrollmentPeriod.start)} -- ${
            makeEndDate(
                formatter,
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
                degree.period
            )
        })
        """.trimIndent()
    }

    private fun makeEduPeriod(enrollmentPeriod: EnrollmentPeriod): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy").withLocale(Locale.US)

        return "${formatter.format(enrollmentPeriod.start)} – ${
            makeEndDate(
                formatter,
                enrollmentPeriod.end
            )
        }"
    }
}
