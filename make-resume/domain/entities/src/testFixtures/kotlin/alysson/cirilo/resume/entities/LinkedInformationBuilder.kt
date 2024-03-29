package alysson.cirilo.resume.entities

import java.net.URI
import java.net.URL

class LinkedInformationBuilder private constructor(
    private val displayName: String,
    private val url: URL,
) {
    constructor() : this(
        displayName = "cool information",
        url = URI("https://www.example.com").toURL(),
    )

    fun displaying(displayName: String) = LinkedInformationBuilder(displayName, url)

    fun linkingTo(urlSpec: String) = LinkedInformationBuilder(displayName, URL(urlSpec))

    fun build(): LinkedInformation {
        return LinkedInformation(displayName, url)
    }
}

fun aLinkedInfo() = LinkedInformationBuilder()
