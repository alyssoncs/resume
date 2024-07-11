package alysson.cirilo.resume.entities

import java.net.URI

data class LinkedInformation(
    val displayName: String,
    val url: URI,
) {
    init {
        require(displayName.isNotBlank()) {
            "displayName cannot be blank"
        }
    }
}
