package alysson.cirilo.resume.drivers.latex.escape

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

fun Resume.escapeLatex(): Resume {
    return this.copy(
        name = name.escape(),
        headline = headline.map(kotlin.String::escape),
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
