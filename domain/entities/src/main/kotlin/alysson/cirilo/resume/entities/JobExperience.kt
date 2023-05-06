package alysson.cirilo.resume.entities

data class JobExperience(
    val company: LinkedInformation,
    val location: String,
    val roles: Role,
)

data class Role(
    val title: String,
    val period: EnrollmentPeriod,
    val bulletPoints: List<ExperienceHighlight>
)

data class ExperienceHighlight(
    val content: List<HighLightContent>
)

sealed interface HighLightContent {
    val displayName: String

    data class PlainText(override val displayName: String): HighLightContent
    data class Skill(val skill: ProfessionalSkill): HighLightContent {
        override val displayName: String = skill.value
    }
}

