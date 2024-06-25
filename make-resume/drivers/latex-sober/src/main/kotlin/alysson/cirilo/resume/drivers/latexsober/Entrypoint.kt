package alysson.cirilo.resume.drivers.latexsober

import alysson.cirilo.resume.drivers.utils.date.educationDateFormatter
import alysson.cirilo.resume.drivers.utils.date.workDateFormatter
import alysson.cirilo.resume.drivers.utils.makeDriver
import alysson.cirilo.resume.drivers.utils.resource.asResource
import alysson.cirilo.resume.entities.BulletPoint
import alysson.cirilo.resume.entities.BulletPointContent
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.LinkedInformation
import alysson.cirilo.resume.entities.ProfessionalSkill
import alysson.cirilo.resume.entities.ProjectOrPublication
import alysson.cirilo.resume.entities.Resume
import alysson.cirilo.resume.entities.Role
import alysson.cirilo.resume.infra.ResumeDriver
import java.time.format.DateTimeFormatter

fun makeLatexSoberDriver(): ResumeDriver {
    return makeLatexSoberDriver(
        template = "/latex-sober-resume-template.tex".asResource(),
        contentPlaceholder = "%%content-goes-here%%",
        workDateFormatter = workDateFormatter,
        educationDateFormatter = educationDateFormatter,
    )
}

internal fun makeLatexSoberDriver(
    template: String,
    contentPlaceholder: String,
    workDateFormatter: DateTimeFormatter,
    educationDateFormatter: DateTimeFormatter,
): ResumeDriver {
    val syntaxFactory = LatexSoberSyntaxFactory(
        template,
        contentPlaceholder,
        workDateFormatter,
        educationDateFormatter,
    )
    return makeDriver(syntaxFactory) { resume ->
        resume.escapeLatex()
    }
}

private fun Resume.escapeLatex(): Resume {
    return this.copy(
        name = name.escape(),
        headline = headline.map(String::escape),
        contactInformation = contactInformation.escape(),
        jobExperiences = jobExperiences.map(JobExperience::escape),
        projectsAndPublications = projectsAndPublications.map(ProjectOrPublication::escape),
        education = education.map(Degree::escape),
    )
}

private fun Degree.escape() = copy(
    institution = institution.escape(),
    location = location.escape(),
    degree = degree.escape(),
)

private fun ProjectOrPublication.escape() = copy(
    title = title.escape(),
    description = description.escape(),
)

private fun JobExperience.escape() = copy(
    company = company.escape(),
    location = location.escape(),
    roles = roles.map(Role::escape),
)

private fun Role.escape() = copy(
    title = title.escape(),
    bulletPoints = bulletPoints.map(BulletPoint::escape),
)

private fun BulletPoint.escape() = copy(
    content = content.map(BulletPointContent::escape),
)

private fun BulletPointContent.escape() = when (this) {
    is BulletPointContent.PlainText -> copy(displayName = displayName.escape())
    is BulletPointContent.Skill -> copy(skill = ProfessionalSkill(displayName.escape()))
}

private fun ContactInformation.escape() = copy(
    email = email.escape(),
    linkedin = linkedin.escape(),
    github = github.escape(),
    location = location.escape(),
)

private fun LinkedInformation.escape() =
    copy(displayName = displayName.escape())

private fun String.escape(): String {
    val reservedCharacters = listOf("&", "%")

    return reservedCharacters.fold(this) { acc, reservedChar ->
        acc.replace(reservedChar, "\\$reservedChar")
    }
}
