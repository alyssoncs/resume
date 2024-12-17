@file:Suppress("Filename")

package alysson.cirilo.resume.serialization

import alysson.cirilo.resume.entities.BulletPoint
import alysson.cirilo.resume.entities.BulletPointContent
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.EnrollmentPeriod
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.LinkedInformation
import alysson.cirilo.resume.entities.ProjectOrPublication
import alysson.cirilo.resume.entities.Resume
import alysson.cirilo.resume.entities.Role
import alysson.cirilo.resume.serialization.models.SerializableContactInformation
import alysson.cirilo.resume.serialization.models.SerializableDegree
import alysson.cirilo.resume.serialization.models.SerializableEnrollmentPeriod
import alysson.cirilo.resume.serialization.models.SerializableJobExperience
import alysson.cirilo.resume.serialization.models.SerializableLinkedInformation
import alysson.cirilo.resume.serialization.models.SerializableProjectOrPublication
import alysson.cirilo.resume.serialization.models.SerializableResume
import alysson.cirilo.resume.serialization.models.SerializableRole
import java.net.URI
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

fun SerializableResume.toDomain(): Resume {
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
        homepage = mapLinkedInfo(contactInfo.homepage),
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

private fun mapProjectOrPublication(projectOrPub: SerializableProjectOrPublication): ProjectOrPublication {
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
        url = URI(info.url),
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

private fun mapBulletPoint(bulletPoint: String): BulletPoint {
    validateMatchingBrackets(bulletPoint)

    return BulletPoint(mapBulletContent(bulletPoint, emptyList())).also {
        validateFlatSkills(it)
    }
}

private fun validateFlatSkills(bulletPoint: BulletPoint) {
    val containsBracketInsideBracket = bulletPoint.content
        .filterIsInstance<BulletPointContent.Skill>()
        .any { it.displayName.contains('{') }
    if (containsBracketInsideBracket) throw ParsingException("Cannot have a skill inside another skill")
}

private tailrec fun mapBulletContent(bulletPoint: String, content: List<BulletPointContent>): List<BulletPointContent> {
    if (bulletPoint.isEmpty()) return content

    val (element, subStr) = if (bulletPoint.first() != '{') {
        val element = extractPlainText(bulletPoint)
        element to bulletPoint.substringAfter(element.displayName)
    } else {
        extractSkill(bulletPoint) to bulletPoint.substringAfter('}')
    }

    return mapBulletContent(subStr, content + element)
}

private fun extractPlainText(str: String): BulletPointContent.PlainText {
    val subStr = str.substringBefore('{')
    return BulletPointContent.PlainText(subStr)
}

private fun extractSkill(str: String): BulletPointContent.Skill {
    val subStr = str.substring(1).substringBefore('}')
    return BulletPointContent.Skill(subStr)
}

private fun validateMatchingBrackets(bulletPoint: String) {
    validateMatchingBrackets(bulletPoint = bulletPoint, insideBracket = bulletPoint.first() == '{')
        .getOrThrow()
}

private tailrec fun validateMatchingBrackets(bulletPoint: String, insideBracket: Boolean): Result<Unit> {
    if (bulletPoint.isEmpty()) {
        return if (insideBracket)
            Result.failure(ParsingException("Missing closing bracket"))
        else
            Result.success(Unit)
    }

    val updatedInsideBracket = if (insideBracket && bulletPoint.first() == '}') {
        false
    } else {
        bulletPoint.first() == '{' || insideBracket
    }

    return validateMatchingBrackets(bulletPoint.substring(1), updatedInsideBracket)
}

class ParsingException(message: String) : RuntimeException(message)
