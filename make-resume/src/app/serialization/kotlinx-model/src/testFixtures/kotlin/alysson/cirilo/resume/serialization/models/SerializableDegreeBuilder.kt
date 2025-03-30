package alysson.cirilo.resume.serialization.models

interface SerializableDegreeBuilder {
    fun at(institutionBuilder: SerializableLinkedInformationBuilder): SerializableDegreeBuilder
    fun at(institution: String): SerializableDegreeBuilder
    fun on(location: String): SerializableDegreeBuilder
    fun tile(degree: String): SerializableDegreeBuilder
    fun during(periodBuilder: SerializableEnrollmentPeriodBuilder): SerializableDegreeBuilder
    fun build(): SerializableDegree
}

private data class SerializableDegreeBuilderImpl(
    private val institutionBuilder: SerializableLinkedInformationBuilder = linkedInfoDto()
        .displaying("Top institution")
        .linkingTo("https://www.example.com"),
    private val location: String = "Brazil",
    private val degree: String = "BSc. in Computer Science",
    private val periodBuilder: SerializableEnrollmentPeriodBuilder = periodDto()
        .from(month = 5, year = 2020)
        .upToNow(),
) : SerializableDegreeBuilder {

    override fun at(institutionBuilder: SerializableLinkedInformationBuilder) =
        copy(institutionBuilder = institutionBuilder)

    override fun at(institution: String) = at(institutionBuilder.displaying(institution))

    override fun on(location: String) = copy(location = location)

    override fun tile(degree: String) = copy(degree = degree)

    override fun during(periodBuilder: SerializableEnrollmentPeriodBuilder) =
        copy(periodBuilder = periodBuilder)

    override fun build(): SerializableDegree {
        return SerializableDegree(
            institution = institutionBuilder.build(),
            location = location,
            degree = degree,
            period = periodBuilder.build(),
        )
    }
}

fun aDegreeDto(): SerializableDegreeBuilder {
    return SerializableDegreeBuilderImpl()
}
