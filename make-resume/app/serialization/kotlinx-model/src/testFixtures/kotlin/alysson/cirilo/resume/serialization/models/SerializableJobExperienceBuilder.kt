package alysson.cirilo.resume.serialization.models

interface SerializableJobExperienceBuilder {
    fun with(role: SerializableRoleBuilder): SerializableJobExperienceBuilder
    fun build(): SerializableJobExperience
}

private data class SerializableJobExperienceBuilderImpl(
    private val company: SerializableLinkedInformationBuilder = linkedInfoDto(),
    private val location: String = "Brazil",
    private val roles: List<SerializableRoleBuilder> = listOf(aRoleDto())
) : SerializableJobExperienceBuilder {

    override fun with(
        role: SerializableRoleBuilder,
    ): SerializableJobExperienceBuilder {
        return copy(roles = listOf(role))
    }

    override fun build(): SerializableJobExperience {
        return SerializableJobExperience(
            company = company.build(),
            location = location,
            roles = roles.map(SerializableRoleBuilder::build)
        )
    }
}

fun aJobExperience(): SerializableJobExperienceBuilder {
    return SerializableJobExperienceBuilderImpl()
}

