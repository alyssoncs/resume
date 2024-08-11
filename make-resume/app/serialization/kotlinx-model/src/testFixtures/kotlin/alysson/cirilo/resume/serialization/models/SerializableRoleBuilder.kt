package alysson.cirilo.resume.serialization.models

interface SerializableRoleBuilder {
    fun from(from: String): SerializableRoleBuilder
    fun upTo(to: String): SerializableRoleBuilder
    fun build(): SerializableRole
}

private data class SerializableRoleBuilderImpl(
    private val title: String = "Software Engineer",
    private val enrollment: SerializableEnrollmentPeriodBuilder = periodDto(),
    private val bullets: List<String> = listOf("worked with kotlin"),
) : SerializableRoleBuilder {

    override fun from(from: String) = copy(enrollment = enrollment.from(from))

    override fun upTo(to: String) = copy(enrollment = enrollment.to(to))

    override fun build(): SerializableRole {
        return SerializableRole(
            title,
            enrollment.build(),
            bullets,
        )
    }
}

fun aRoleDto(): SerializableRoleBuilder {
    return SerializableRoleBuilderImpl()
}

