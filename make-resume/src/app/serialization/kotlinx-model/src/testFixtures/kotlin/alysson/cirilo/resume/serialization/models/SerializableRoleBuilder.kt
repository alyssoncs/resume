package alysson.cirilo.resume.serialization.models

interface SerializableRoleBuilder {
    fun `as`(title: String): SerializableRoleBuilder
    fun from(from: String): SerializableRoleBuilder
    fun upTo(to: String): SerializableRoleBuilder
    fun upToNow(): SerializableRoleBuilder
    fun withEmptyBullets(): SerializableRoleBuilder = bullets(emptyList())
    fun bullet(bullet: String): SerializableRoleBuilder = bullets(listOf(bullet))
    fun bullets(vararg bullets: String): SerializableRoleBuilder = bullets(bullets.toList())
    fun bullets(bullets: List<String>): SerializableRoleBuilder
    fun build(): SerializableRole
}

private data class SerializableRoleBuilderImpl(
    private val title: String = "Software Engineer",
    private val enrollment: SerializableEnrollmentPeriodBuilder = periodDto(),
    private val bullets: List<String> = listOf("worked with kotlin"),
) : SerializableRoleBuilder {
    override fun `as`(title: String): SerializableRoleBuilder = copy(title = title)

    override fun from(from: String) = copy(enrollment = enrollment.from(from))

    override fun upTo(to: String) = copy(enrollment = enrollment.to(to))

    override fun upToNow(): SerializableRoleBuilder =
        copy(enrollment = enrollment.upToNow())

    override fun bullets(bullets: List<String>): SerializableRoleBuilder = copy(
        bullets = bullets,
    )

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
