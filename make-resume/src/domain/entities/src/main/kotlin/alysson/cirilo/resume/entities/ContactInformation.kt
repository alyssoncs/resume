package alysson.cirilo.resume.entities

data class ContactInformation(
    val homepage: LinkedInformation,
    val email: LinkedInformation,
    val linkedin: LinkedInformation,
    val github: LinkedInformation,
    val location: LinkedInformation,
)
