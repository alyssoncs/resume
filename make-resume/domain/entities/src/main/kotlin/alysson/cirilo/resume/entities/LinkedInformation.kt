package alysson.cirilo.resume.entities

import java.net.URL

data class LinkedInformation(
    val displayName: String,
    val url: URL,
) {
    init {
        require(displayName.isNotBlank()) {
            "displayName cannot be blank"
        }
    }
}