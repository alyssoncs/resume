package alysson.cirilo.resume.entities.test.databuilders

import alysson.cirilo.resume.entities.JobExperience

class JobExperienceBuilder {
    private var roleBuilders: List<RoleBuilder> = listOf(RoleBuilder())
    private var companyBuilder: LinkedInformationBuilder = aLinkedInfo()
        .displaying("Cool Company")
        .linkingTo("https://www.coolcompany.com")

    private var location: String = "Remote"

    fun with(roleBuilders: List<RoleBuilder>) = builderMethod {
        this.roleBuilders = roleBuilders
    }

    fun with(roleBuilder: RoleBuilder) = with(listOf(roleBuilder))

    fun withNoRole() = with(emptyList())

    fun append(roleBuilder: RoleBuilder) = builderMethod {
        this.roleBuilders += roleBuilder
    }

    fun on(company: String) = builderMethod {
        companyBuilder = companyBuilder.displaying(company)
    }

    fun on(companyBuilder: LinkedInformationBuilder) = builderMethod {
        this.companyBuilder = companyBuilder
    }

    fun basedOn(location: String) = builderMethod {
        this.location = location
    }

    fun build(): JobExperience{
        return JobExperience(
            company = companyBuilder.build(),
            location = location,
            roles = roleBuilders.map(RoleBuilder::build),
        )
    }
}

fun aJobExperience() = JobExperienceBuilder()

fun aCompany(companyName: String, companyUrl: String) = aLinkedInfo()
    .displaying(companyName)
    .linkingTo(companyUrl)
