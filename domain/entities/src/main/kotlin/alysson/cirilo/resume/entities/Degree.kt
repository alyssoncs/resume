package alysson.cirilo.resume.entities

data class Degree(
    val institution: LinkedInformation,
    val location: String,
    val degree: String,
    val period: EnrollmentPeriod,
)
