package alysson.cirilo.resume.entities.testbuilders

import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.LinkedInformation
import java.net.URL

class JobExperienceBuilder {
    private var roleBuilders: List<RoleBuilder> = listOf(RoleBuilder())

    fun with(roleBuilders: List<RoleBuilder>) = builderMethod {
        this.roleBuilders = roleBuilders
    }

    fun with(roleBuilder: RoleBuilder) = with(listOf(roleBuilder))

    fun withNoRole() = with(emptyList())

    fun append(roleBuilder: RoleBuilder) = builderMethod {
        this.roleBuilders += roleBuilder
    }

    fun build(): JobExperience{
        return JobExperience(
            company = LinkedInformation(
                displayName = "Cool Company",
                url = URL("https://www.coolcompany.com")
            ),
            location = "Remote",
            roles = roleBuilders.map(RoleBuilder::build),
        )
    }
}

fun aJobExperience() = JobExperienceBuilder()
