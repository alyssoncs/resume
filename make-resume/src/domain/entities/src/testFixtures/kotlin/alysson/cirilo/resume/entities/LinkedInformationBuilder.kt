package alysson.cirilo.resume.entities

import java.net.URI

class LinkedInformationBuilder private constructor(
    private val displayName: String,
    private val url: URI,
) {
    constructor() : this(
        displayName = "cool information",
        url = URI("https://www.example.com"),
    )

    fun displaying(displayName: String) = LinkedInformationBuilder(displayName, url)

    fun linkingTo(urlSpec: String) = LinkedInformationBuilder(displayName, URI(urlSpec))

    fun build(): LinkedInformation {
        return LinkedInformation(displayName, url)
    }
}

fun aLinkedInfo() = LinkedInformationBuilder()
