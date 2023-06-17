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
    ) : this(title, EnrollmentPeriod(start, EnrollmentPeriod.EndDate.Past(end)), bulletPoints.toList())

    constructor(
        title: String,
        start: LocalDate,
        vararg bulletPoints: BulletPoint,
    ) : this(title, EnrollmentPeriod(start, EnrollmentPeriod.EndDate.Present), bulletPoints.toList())
}

data class BulletPoint(
    val content: List<BulletPointContent>
) {
    constructor(vararg bullets: BulletPointContent) : this(bullets.toList())
    constructor(vararg bullets: String) : this(bullets.mapIndexed { idx, bullet ->
        if (idx % 2 != 0)
            BulletPointContent.Skill(ProfessionalSkill(bullet))
        else
            BulletPointContent.PlainText(bullet) }
    )

    init {
        require(content.isNotEmpty()) { "A bullet point must have content." }
    }
}

sealed interface BulletPointContent {
    val displayName: String

    data class PlainText(override val displayName: String) : BulletPointContent {
        init { validate() }
    }
    data class Skill(val skill: ProfessionalSkill) : BulletPointContent {
        override val displayName: String = skill.value

        init { validate() }
    }

    companion object {
        private fun BulletPointContent.validate() =
            require(displayName.isNotBlank()) { "A bullet point content cannot be blank" }
    }
}
