package alysson.cirilo.resume.serialization.models

interface SerializableJobExperienceBuilder {
    fun with(role: SerializableRoleBuilder): SerializableJobExperienceBuilder = with(listOf(role))
    fun with(vararg roles: SerializableRoleBuilder): SerializableJobExperienceBuilder = with(roles.toList())
    fun with(roles: List<SerializableRoleBuilder>): SerializableJobExperienceBuilder
    fun build(): SerializableJobExperience
}

private data class SerializableJobExperienceBuilderImpl(
    private val company: SerializableLinkedInformationBuilder = linkedInfoDto(),
    private val location: String = "Brazil",
    private val roles: List<SerializableRoleBuilder> = listOf(aRoleDto())
) : SerializableJobExperienceBuilder {

    override fun with(roles: List<SerializableRoleBuilder>): SerializableJobExperienceBuilder = copy(
        roles = roles,
    )

    override fun build(): SerializableJobExperience {
        return SerializableJobExperience(
            company = company.build(),
            location = location,
            roles = roles.map(SerializableRoleBuilder::build)
        )
    }
}

fun aJobExperienceDto(): SerializableJobExperienceBuilder {
    return SerializableJobExperienceBuilderImpl()
}

