package alysson.cirilo.resume.entities

class JobExperienceBuilder private constructor(
    private val roleBuilders: List<RoleBuilder> = listOf(RoleBuilder()),
    private val companyBuilder: LinkedInformationBuilder = aLinkedInfo()
        .displaying("Cool Company")
        .linkingTo("https://www.coolcompany.com"),
    private val location: String = "Remote",
) {

    constructor() : this(
        roleBuilders = listOf(RoleBuilder()),
        companyBuilder = aLinkedInfo()
            .displaying("Cool Company")
            .linkingTo("https://www.coolcompany.com"),
        location = "Remote",
    )

    fun with(roleBuilders: List<RoleBuilder>) =
        JobExperienceBuilder(roleBuilders, companyBuilder, location)

    fun with(roleBuilder: RoleBuilder) = with(listOf(roleBuilder))

    fun withNoRoles() = with(emptyList())

    fun append(roleBuilder: RoleBuilder) = with(roleBuilders + roleBuilder)

    fun on(company: String) = on(companyBuilder.displaying(company))

    fun on(companyBuilder: LinkedInformationBuilder) =
        JobExperienceBuilder(roleBuilders, companyBuilder, location)

    fun basedOn(location: String) = JobExperienceBuilder(roleBuilders, companyBuilder, location)

    fun build(): JobExperience {
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
