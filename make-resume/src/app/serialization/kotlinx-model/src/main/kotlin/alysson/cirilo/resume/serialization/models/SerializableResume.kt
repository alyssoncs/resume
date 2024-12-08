package alysson.cirilo.resume.serialization.models

import kotlinx.serialization.Serializable

@Serializable
data class SerializableResume(
    val name: String,
    val headline: List<String>,
    val contactInfo: SerializableContactInformation,
    val experiences: List<SerializableJobExperience>,
    val projectsAndPublications: List<SerializableProjectOrPublication>,
    val education: List<SerializableDegree>,
)

@Serializable
data class SerializableContactInformation(
    val homepage: SerializableLinkedInformation,
    val email: SerializableLinkedInformation,
    val linkedin: SerializableLinkedInformation,
    val github: SerializableLinkedInformation,
    val location: SerializableLinkedInformation,
)

@Serializable
data class SerializableJobExperience(
    val company: SerializableLinkedInformation,
    val location: String,
    val roles: List<SerializableRole>,
)

@Serializable
data class SerializableRole(
    val title: String,
    val period: SerializableEnrollmentPeriod,
    val bulletPoints: List<String>,
)

@Serializable
data class SerializableProjectOrPublication(
    val title: SerializableLinkedInformation,
    val description: String,
)

@Serializable
data class SerializableDegree(
    val institution: SerializableLinkedInformation,
    val location: String,
    val degree: String,
    val period: SerializableEnrollmentPeriod,
)

@Serializable
data class SerializableLinkedInformation(
    val displayName: String,
    val url: String,
)

@Serializable
data class SerializableEnrollmentPeriod(
    val from: String,
    val to: String = CURRENT,
) {
    init {
        require(dateRegex.matches(from)) {
            "\"${::from.name}\" should be in the \"MM-yyyy\" format, but was $from"
        }

        require(dateRegex.matches(to) || to == CURRENT) {
            "\"${::to.name}\" should be \"$CURRENT\" or be in the \"MM-yyyy\" format, but was \"$to\""
        }
    }

    val isCurrent: Boolean
        get() = to == CURRENT

    companion object {
        private val dateRegex = Regex("\\d{2}-\\d{4}")
        const val CURRENT = "now"
    }
}
