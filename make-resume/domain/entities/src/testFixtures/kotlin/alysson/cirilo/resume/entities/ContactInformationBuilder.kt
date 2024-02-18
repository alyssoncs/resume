package alysson.cirilo.resume.entities

class ContactInformationBuilder private constructor(
    private val emailBuilder: LinkedInformationBuilder,
    private val linkedinBuilder: LinkedInformationBuilder,
    private val githubBuilder: LinkedInformationBuilder,
    private val locationBuilder: LinkedInformationBuilder,
) {

    constructor() : this(
        emailBuilder = aLinkedInfo()
            .displaying("alysson.cirilo@gmail.com")
            .linkingTo("mailto:alysson.cirilo@gmail.com"),
        linkedinBuilder = aLinkedInfo()
            .displaying("linkedin.com/in/alysson-cirilo")
            .linkingTo("https://www.linkedin.com/in/alysson-cirilo"),
        githubBuilder = aLinkedInfo()
            .displaying("github.com/alyssoncs")
            .linkingTo("https://www.github.com/alyssoncs"),
        locationBuilder = aLinkedInfo()
            .displaying("Remote, Brazil")
            .linkingTo("https://www.example.com"),
    )

    fun email(emailBuilder: LinkedInformationBuilder) =
        ContactInformationBuilder(emailBuilder, linkedinBuilder, githubBuilder, locationBuilder)

    fun linkedin(linkedinBuilder: LinkedInformationBuilder) =
        ContactInformationBuilder(emailBuilder, linkedinBuilder, githubBuilder, locationBuilder)

    fun github(githubBuilder: LinkedInformationBuilder) =
        ContactInformationBuilder(emailBuilder, linkedinBuilder, githubBuilder, locationBuilder)

    fun location(locationBuilder: LinkedInformationBuilder) =
        ContactInformationBuilder(emailBuilder, linkedinBuilder, githubBuilder, locationBuilder)

    fun build(): ContactInformation {
        return ContactInformation(
            email = emailBuilder.build(),
            linkedin = linkedinBuilder.build(),
            github = githubBuilder.build(),
            location = locationBuilder.build(),
        )
    }
}

fun contactInfo() = ContactInformationBuilder()
