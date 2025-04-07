package alysson.cirilo.resume.drivers.utils

import alysson.cirilo.resume.drivers.utils.indent.reindent
import alysson.cirilo.resume.entities.BulletPoint
import alysson.cirilo.resume.entities.BulletPointContent
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.EnrollmentPeriod
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.ProjectOrPublication
import alysson.cirilo.resume.entities.Role
import java.time.format.DateTimeFormatter

internal class FakeResumeBuilder private constructor(
    private val workDateFormatter: DateTimeFormatter,
    private val educationDateFormatter: DateTimeFormatter,
    private val output: String,
) : ResumeBuilder {

    constructor(
        workDateFormatter: DateTimeFormatter,
        educationDateFormatter: DateTimeFormatter,
    ) : this(workDateFormatter, educationDateFormatter, output = "")

    override fun addHeader(
        name: String,
        headline: List<String>,
        contactInformation: ContactInformation,
    ): ResumeBuilder {
        val contactInfo = """
            * ${contactInformation.email.displayName} (${contactInformation.email.url})
            * ${contactInformation.homepage.displayName} (${contactInformation.homepage.url})
            * ${contactInformation.linkedin.displayName} (${contactInformation.linkedin.url})
            * ${contactInformation.github.displayName} (${contactInformation.github.url})
            * ${contactInformation.location.displayName} (${contactInformation.location.url})
        """.reindent(1)
        val header = """
            $name
            ${headline.joinToString()}

            > Contact info
        """.trimIndent()
        return new(header + "\n" + contactInfo)
    }

    override fun startSection(name: String): ResumeBuilder {
        val prefix = if (output.isNotEmpty()) "\n" else ""
        return new("$prefix> $name")
    }

    override fun makeExperiences(jobExperiences: List<JobExperience>): ResumeBuilder {
        val exp = jobExperiences.joinToString(separator = "\n\n", transform = ::makeExperience)
        return new(exp)
    }

    override fun makeProjectsAndPublications(projectsAndPublications: List<ProjectOrPublication>): ResumeBuilder {
        val projects = projectsAndPublications.joinToString(separator = "\n", transform = ::makeProjectOrPublication)
            .reindent(1)
        return new(projects)
    }

    override fun makeEducation(education: List<Degree>): ResumeBuilder {
        val edu = education.joinToString(separator = "\n", transform = ::makeDegree)
            .reindent(1)
        return new(edu)
    }

    override fun build(): String {
        return output
    }

    private fun new(output: String): FakeResumeBuilder {
        return FakeResumeBuilder(workDateFormatter, educationDateFormatter, updateOutput(output))
    }

    private fun updateOutput(newOutput: String): String {
        val separator = if (output.isNotEmpty()) "\n" else ""
        return "$output$separator$newOutput"
    }

    private fun makeExperience(jobExperience: JobExperience): String {
        val roles = makeRoles(jobExperience.roles).reindent(1)
        return """
            Experience @ ${jobExperience.company.displayName} (${jobExperience.company.url}), ${jobExperience.location}
        """.trimIndent() + "\n" + roles
    }

    private fun makeRoles(roles: List<Role>): String {
        return roles.joinToString(separator = "\n", transform = ::makeRole)
    }

    private fun makeRole(role: Role): String {
        val roleStr = """
            * ${role.title} (${makeEnrollmentPeriod(workDateFormatter, role.period)})
        """.trimIndent()
        val bullets = makeBulletPoints(role.bulletPoints).reindent(1)

        return roleStr + "\n" + bullets
    }

    private fun makeBulletPoints(bulletPoints: List<BulletPoint>): String {
        return bulletPoints.joinToString(separator = "\n", transform = ::makeBulletPoint)
    }

    private fun makeBulletPoint(bulletPoint: BulletPoint): String {
        return bulletPoint.content.joinToString(prefix = "- ", separator = "") {
            when (it) {
                is BulletPointContent.PlainText -> it.displayName
                is BulletPointContent.Skill -> "{${it.displayName}}"
            }
        }
    }

    private fun makeProjectOrPublication(projectOrPublication: ProjectOrPublication): String {
        return """
            * ${projectOrPublication.title.displayName} (${projectOrPublication.title.url}): ${projectOrPublication.description}
        """.trimIndent()
    }

    private fun makeDegree(degree: Degree): String {
        val period = makeEnrollmentPeriod(educationDateFormatter, degree.period)
        return """
            * ${degree.degree} @ ${degree.institution.displayName} (${degree.institution.url}), ${degree.location} $period
        """.trimIndent()
    }

    private fun makeEnrollmentPeriod(
        formatter: DateTimeFormatter,
        enrollmentPeriod: EnrollmentPeriod,
    ): String {
        val start = formatter.format(enrollmentPeriod.start)
        val end = makeEndDate(formatter, enrollmentPeriod.end)
        return "$start - $end"
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
}
