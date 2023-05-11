package alysson.cirilo.resume.entities.test.databuilders

import alysson.cirilo.resume.entities.LinkedInformation
import java.net.URL

class LinkedInformationBuilder {
    private var displayName: String = "cool information"
    private var url: URL = URL("https://www.example.com")

    fun displaying(displayName: String) = builderMethod {
        this.displayName = displayName
    }

    fun linkingTo(urlSpec: String) = builderMethod {
        this.url = URL(urlSpec)
    }

    fun build(): LinkedInformation {
        return LinkedInformation(displayName, url)
    }
}

fun aLinkedInfo() = LinkedInformationBuilder()
