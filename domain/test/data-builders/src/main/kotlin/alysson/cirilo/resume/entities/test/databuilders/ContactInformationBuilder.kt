package alysson.cirilo.resume.entities.test.databuilders

import alysson.cirilo.resume.entities.ContactInformation

class ContactInformationBuilder {
    private var emailBuilder: LinkedInformationBuilder = aLinkedInfo()
        .displaying("alysson.cirilo@gmail.com")
        .linkingTo("mailto:alysson.cirilo@gmail.com")

    private var linkedinBuilder: LinkedInformationBuilder = aLinkedInfo()
        .displaying("linkedin.com/in/alysson-cirilo")
        .linkingTo("https://www.linkedin.com/in/alysson-cirilo")

    private var githubBuilder: LinkedInformationBuilder = aLinkedInfo()
        .displaying("github.com/alyssoncs")
        .linkingTo("https://www.github.com/alyssoncs")

    private var locationBuilder: LinkedInformationBuilder = aLinkedInfo()
        .displaying("Remote, Brazil")
        .linkingTo("https://www.example.com")

    fun email(emailBuilder: LinkedInformationBuilder) = builderMethod {
        this.emailBuilder = emailBuilder
    }

    fun linkedin(linkedinBuilder: LinkedInformationBuilder) = builderMethod {
        this.linkedinBuilder = linkedinBuilder
    }

    fun github(githubBuilder: LinkedInformationBuilder) = builderMethod {
        this.githubBuilder = githubBuilder
    }

    fun location(locationBuilder: LinkedInformationBuilder) = builderMethod {
        this.locationBuilder = locationBuilder
    }

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
