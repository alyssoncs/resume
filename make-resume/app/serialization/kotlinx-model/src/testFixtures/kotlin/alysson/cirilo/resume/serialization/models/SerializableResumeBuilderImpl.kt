package alysson.cirilo.resume.serialization.models

interface SerializableResumeBuilder {
    fun with(experience: SerializableJobExperienceBuilder): SerializableResumeBuilder
    fun build(): SerializableResume
}

fun aResumeDto(): SerializableResumeBuilder {
    return SerializableResumeBuilderImpl()
}

private data class SerializableResumeBuilderImpl(
    private val name: String = "alysson",
    private val headline: List<String> = listOf("software engineer"),
    private val contactInfo: SerializableContactInformation = SerializableContactInformation(
        email = linkedInfoDto()
            .displaying("alysson.cirilo@gmail.com")
            .linkingTo("mailto:alysson.cirilo@gmail.com")
            .build(),
        linkedin = linkedInfoDto()
            .displaying("linkedin.com/in/alysson-cirilo")
            .linkingTo("https://www.linkedin.com/in/alysson-cirilo")
            .build(),
        github = linkedInfoDto()
            .displaying("github.com/alyssoncs")
            .linkingTo("https://www.github.com/alyssoncs")
            .build(),
        location = linkedInfoDto()
            .displaying("Remote, Brazil")
            .linkingTo("https://www.example.com")
            .build(),
    ),
    private val experiences: List<SerializableJobExperienceBuilder> = emptyList(),
    private val projectsAndPublications: List<SerializableProjectOrPublication> = emptyList(),
    private val education: List<SerializableDegree> = emptyList(),
) : SerializableResumeBuilder {

    override fun with(experience: SerializableJobExperienceBuilder) = copy(
        experiences = listOf(experience),
    )

    override fun build(): SerializableResume {
        return SerializableResume(
            name = name,
            headline = headline,
            contactInfo = contactInfo,
            experiences = experiences.map(SerializableJobExperienceBuilder::build),
            projectsAndPublications = projectsAndPublications,
            education = education,
        )
    }
}
