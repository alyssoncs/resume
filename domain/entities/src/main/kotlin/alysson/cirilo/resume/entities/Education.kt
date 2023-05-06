package alysson.cirilo.resume.entities

data class Education(
    val institution: LinkedInformation,
    val location: String,
    val degree: String,
    val period: EnrollmentPeriod,
)
