package alysson.cirilo.resume.serialization.models

interface SerializableLinkedInformationBuilder {
    fun displaying(displayName: String): SerializableLinkedInformationBuilder
    fun linkingTo(url: String): SerializableLinkedInformationBuilder
    fun build(): SerializableLinkedInformation
}

private data class SerializableLinkedInformationBuilderImpl(
    private val displayName: String = "cool information",
    private val url: String = "https://www.example.com",
) : SerializableLinkedInformationBuilder {

    override fun displaying(displayName: String) = copy(url = url)

    override fun linkingTo(url: String) = copy(displayName = displayName)

    override fun build(): SerializableLinkedInformation {
        return SerializableLinkedInformation(displayName, url)
    }
}

fun linkedInfoDto(): SerializableLinkedInformationBuilder {
    return SerializableLinkedInformationBuilderImpl()
}
