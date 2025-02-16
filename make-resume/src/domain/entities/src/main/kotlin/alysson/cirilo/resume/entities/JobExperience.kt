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
    val bulletPoints: List<BulletPoint>,
)

data class BulletPoint(
    val content: List<BulletPointContent>,
) {
    init {
        require(content.isNotEmpty()) { "A bullet point must have content." }
    }
}

sealed interface BulletPointContent {
    val displayName: String

    data class PlainText(override val displayName: String) : BulletPointContent {
        init {
            validate()
        }
    }

    data class Skill(override val displayName: String) : BulletPointContent {
        init {
            validate()
        }
    }

    companion object {
        private fun BulletPointContent.validate() =
            require(displayName.isNotBlank()) { "A bullet point content cannot be blank" }
    }
}
