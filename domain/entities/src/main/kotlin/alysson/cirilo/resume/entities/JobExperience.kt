package alysson.cirilo.resume.entities

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
)

data class BulletPoint(
    val content: List<BulletPointContent>
) {
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

