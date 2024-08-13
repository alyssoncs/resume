package alysson.cirilo.resume.serialization.models

interface SerializableProjectOrPublicationBuilder {
    fun named(name: String): SerializableProjectOrPublicationBuilder
    fun hostedOn(urlSpec: String): SerializableProjectOrPublicationBuilder
    fun description(description: String): SerializableProjectOrPublicationBuilder
    fun build(): SerializableProjectOrPublication
}

private data class SerializableProjectOrPublicationBuilderImpl(
    private val title: SerializableLinkedInformationBuilder = linkedInfoDto()
        .displaying("cool project")
        .linkingTo("https://www.github.com/alyssoncs/coolproject"),
    private val description: String = "a very cool project",
) : SerializableProjectOrPublicationBuilder {

    override fun named(name: String) = copy(title = title.displaying(name))

    override fun hostedOn(urlSpec: String) = copy(title = title.linkingTo(urlSpec))

    override fun description(description: String) = copy(description = description)

    override fun build(): SerializableProjectOrPublication {
        return SerializableProjectOrPublication(
            title = title.build(),
            description = description,
        )
    }
}

fun aProjectDto(): SerializableProjectOrPublicationBuilder {
    return SerializableProjectOrPublicationBuilderImpl()
}
