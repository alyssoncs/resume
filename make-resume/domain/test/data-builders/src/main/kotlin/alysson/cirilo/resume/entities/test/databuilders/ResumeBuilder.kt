package alysson.cirilo.resume.entities.test.databuilders

import alysson.cirilo.resume.entities.Resume

class ResumeBuilder private constructor(
    private val name: String,
    private val headline: List<String>,
    private val contactInformationBuilder: ContactInformationBuilder,
    private val jobExperienceBuilders: List<JobExperienceBuilder>,
    private val projectAndPublicationBuilders: List<ProjectOrPublicationBuilder>,
    private val degreeBuilders: List<DegreeBuilder>,
) {
    constructor() : this(
        name = "Alysson Cirilo",
        headline = listOf("Android Developer", "Software Engineer"),
        contactInformationBuilder = ContactInformationBuilder(),
        jobExperienceBuilders = listOf(JobExperienceBuilder()),
        projectAndPublicationBuilders = listOf(ProjectOrPublicationBuilder()),
        degreeBuilders = listOf(DegreeBuilder()),
    )

    fun from(name: String) = ResumeBuilder(
        name,
        headline,
        contactInformationBuilder,
        jobExperienceBuilders,
        projectAndPublicationBuilders,
        degreeBuilders,
    )

    fun withHeadline(headline: List<String>) = ResumeBuilder(
        name,
        headline,
        contactInformationBuilder,
        jobExperienceBuilders,
        projectAndPublicationBuilders,
        degreeBuilders,
    )

    fun with(contactInformationBuilder: ContactInformationBuilder) = ResumeBuilder(
        name,
        headline,
        contactInformationBuilder,
        jobExperienceBuilders,
        projectAndPublicationBuilders,
        degreeBuilders,
    )

    fun withEmptyHeadline() = withHeadline(emptyList())

    fun with(jobExperienceBuilders: List<JobExperienceBuilder>) = ResumeBuilder(
        name,
        headline,
        contactInformationBuilder,
        jobExperienceBuilders,
        projectAndPublicationBuilders,
        degreeBuilders,
    )

    fun with(jobExperienceBuilder: JobExperienceBuilder) = with(listOf(jobExperienceBuilder))

    fun withNoExperience() = with(emptyList<JobExperienceBuilder>())

    fun append(jobExperienceBuilder: JobExperienceBuilder) =
        with(jobExperienceBuilders + jobExperienceBuilder)

    @JvmName("withProjectsAndPublications")
    fun with(projectAndPublicationBuilders: List<ProjectOrPublicationBuilder>) = ResumeBuilder(
        name,
        headline,
        contactInformationBuilder,
        jobExperienceBuilders,
        projectAndPublicationBuilders,
        degreeBuilders,
    )

    fun with(projectsAndPublicationsBuilder: ProjectOrPublicationBuilder) =
        with(listOf(projectsAndPublicationsBuilder))

    fun withNoProjectsOrPublications() = with(emptyList<ProjectOrPublicationBuilder>())

    @JvmName("withDegrees")
    fun with(degreeBuilders: List<DegreeBuilder>) = ResumeBuilder(
        name,
        headline,
        contactInformationBuilder,
        jobExperienceBuilders,
        projectAndPublicationBuilders,
        degreeBuilders,
    )

    fun with(degreeBuilder: DegreeBuilder) = with(listOf(degreeBuilder))

    fun withNoEducation() = with(emptyList<DegreeBuilder>())

    fun build(): Resume {
        return Resume(
            name = name,
            headline = headline,
            contactInformation = contactInformationBuilder.build(),
            jobExperiences = jobExperienceBuilders.map(JobExperienceBuilder::build),
            projectsAndPublications = projectAndPublicationBuilders.map(ProjectOrPublicationBuilder::build),
            education = degreeBuilders.map(DegreeBuilder::build),
        )
    }
}

fun aResume(): ResumeBuilder = ResumeBuilder()
