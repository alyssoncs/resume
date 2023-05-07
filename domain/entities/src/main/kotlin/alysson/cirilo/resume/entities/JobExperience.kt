package alysson.cirilo.resume.entities

import java.time.LocalDate

data class JobExperience(
    val company: LinkedInformation,
    val location: String,
    val roles: List<Role>,
) {
    init {
        require(roles.isNotEmpty()) { "Cannot have a job experience without a role." }
    }
}

data class Role(
    val title: String,
    val period: EnrollmentPeriod,
    val bulletPoints: List<BulletPoint>
) {
    constructor(
        title: String,
        start: LocalDate,
        end: LocalDate,
        vararg bulletPoints: BulletPoint,
    ): this(title, EnrollmentPeriod(start, EnrollmentPeriod.EndDate.Past(end)), bulletPoints.toList())

    constructor(
        title: String,
        start: LocalDate,
        vararg bulletPoints: BulletPoint,
    ): this(title, EnrollmentPeriod(start, EnrollmentPeriod.EndDate.Present), bulletPoints.toList())
}

data class BulletPoint(
    val content: List<BulletPointContent>
) {
    constructor(vararg bullets: BulletPointContent) : this(bullets.toList())

    init {
        require(content.isNotEmpty()) { "A bullet point must have content." }
    }
}

sealed interface BulletPointContent {
    val displayName: String

    data class PlainText(override val displayName: String): BulletPointContent
    data class Skill(val skill: ProfessionalSkill): BulletPointContent {
        override val displayName: String = skill.value
    }
}

