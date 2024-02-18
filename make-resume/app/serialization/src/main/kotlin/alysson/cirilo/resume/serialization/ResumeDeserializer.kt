package alysson.cirilo.resume.serialization

import alysson.cirilo.resume.entities.BulletPoint
import alysson.cirilo.resume.entities.BulletPointContent
import alysson.cirilo.resume.entities.BulletPointContent.PlainText
import alysson.cirilo.resume.entities.BulletPointContent.Skill
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.EnrollmentPeriod
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.LinkedInformation
import alysson.cirilo.resume.entities.ProfessionalSkill
import alysson.cirilo.resume.entities.ProjectOrPublication
import alysson.cirilo.resume.entities.Resume
import alysson.cirilo.resume.entities.Role
import alysson.cirilo.resume.serialization.models.SerializableBulletPoint
import alysson.cirilo.resume.serialization.models.SerializableContactInformation
import alysson.cirilo.resume.serialization.models.SerializableDegree
import alysson.cirilo.resume.serialization.models.SerializableEnrollmentPeriod
import alysson.cirilo.resume.serialization.models.SerializableJobExperience
import alysson.cirilo.resume.serialization.models.SerializableLinkedInformation
import alysson.cirilo.resume.serialization.models.SerializableProjectOrPublication
import alysson.cirilo.resume.serialization.models.SerializableResume
import alysson.cirilo.resume.serialization.models.SerializableRole
import kotlinx.serialization.json.Json
import java.net.URI
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

fun deserialize(jsonResume: String): Resume {
    val serializableResume = Json.decodeFromString<SerializableResume>(jsonResume)
    return serializableResume.toDomain()
}

private fun SerializableResume.toDomain(): Resume {
    return Resume(
        name = name,
        headline = headline,
        contactInformation = mapContactInfo(contactInfo),
        jobExperiences = experiences.map(::mapExperience),
        projectsAndPublications = projectsAndPublications.map(::mapProjectOrPublication),
        education = education.map(::mapDegree),
    )
}

private fun mapContactInfo(contactInfo: SerializableContactInformation): ContactInformation {
    return ContactInformation(
        email = mapLinkedInfo(contactInfo.email),
        linkedin = mapLinkedInfo(contactInfo.linkedin),
        github = mapLinkedInfo(contactInfo.github),
        location = mapLinkedInfo(contactInfo.location),
    )
}

private fun mapExperience(experience: SerializableJobExperience): JobExperience {
    return JobExperience(
        company = mapLinkedInfo(experience.company),
        location = experience.location,
        roles = experience.roles.map(::mapRole),
    )
}

private fun mapProjectOrPublication(
    projectOrPub: SerializableProjectOrPublication,
): ProjectOrPublication {
    return ProjectOrPublication(
        title = mapLinkedInfo(projectOrPub.title),
        description = projectOrPub.description,
    )
}

private fun mapDegree(degree: SerializableDegree): Degree {
    return Degree(
        institution = mapLinkedInfo(degree.institution),
        location = degree.location,
        degree = degree.degree,
        period = mapEnrollmentPeriod(degree.period),
    )
}

private fun mapLinkedInfo(info: SerializableLinkedInformation): LinkedInformation {
    return LinkedInformation(
        displayName = info.displayName,
        url = URI(info.url).toURL(),
    )
}

private fun mapRole(role: SerializableRole): Role {
    return Role(
        title = role.title,
        period = mapEnrollmentPeriod(role.period),
        bulletPoints = mapBulletPoints(role),
    )
}

private fun mapEnrollmentPeriod(period: SerializableEnrollmentPeriod): EnrollmentPeriod {
    return EnrollmentPeriod(
        start = mapDate(period.from),
        end = mapEndDate(period),
    )
}

private fun mapEndDate(period: SerializableEnrollmentPeriod): EnrollmentPeriod.EndDate {
    return if (period.isCurrent)
        EnrollmentPeriod.EndDate.Present
    else
        EnrollmentPeriod.EndDate.Past(mapDate(period.to))
}

private fun mapDate(strDate: String): YearMonth {
    val formatter = DateTimeFormatter.ofPattern("MM-yyyy").withLocale(Locale.US)
    return YearMonth.parse(strDate, formatter)
}

private fun mapBulletPoints(it: SerializableRole): List<BulletPoint> {
    return it.bulletPoints.map(::mapBulletPoint)
}

private fun mapBulletPoint(bulletContent: List<SerializableBulletPoint>): BulletPoint {
    return BulletPoint(bulletContent.map(::mapBulletContent))
}

private fun mapBulletContent(bulletPoint: SerializableBulletPoint): BulletPointContent {
    return when (bulletPoint.type) {
        SerializableBulletPoint.Type.PlainText -> PlainText(bulletPoint.content)
        SerializableBulletPoint.Type.Skill -> Skill(ProfessionalSkill(bulletPoint.content))
    }
}
