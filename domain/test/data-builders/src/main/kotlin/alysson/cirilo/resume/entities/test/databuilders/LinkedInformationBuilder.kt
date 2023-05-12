package alysson.cirilo.resume.entities.test.databuilders

import alysson.cirilo.resume.entities.LinkedInformation
import java.net.URL

class LinkedInformationBuilder private constructor(
    private val displayName: String,
    private val url: URL,
) {
    constructor(): this(
        displayName = "cool information",
        url = URL("https://www.example.com"),
    )

    fun displaying(displayName: String) = LinkedInformationBuilder(displayName, url)

    fun linkingTo(urlSpec: String) = LinkedInformationBuilder(displayName, URL(urlSpec))

    fun build(): LinkedInformation {
        return LinkedInformation(displayName, url)
    }
}

fun aLinkedInfo() = LinkedInformationBuilder()
