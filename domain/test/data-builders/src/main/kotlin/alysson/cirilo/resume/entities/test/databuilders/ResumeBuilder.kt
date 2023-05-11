package alysson.cirilo.resume.entities.test.databuilders

import alysson.cirilo.resume.entities.Resume

class ResumeBuilder {
    private var name: String = "Alysson Cirilo"
    private var headline: List<String> = listOf("Android Developer", "Software Engineer")
    private var contactInformationBuilder: ContactInformationBuilder = ContactInformationBuilder()
    private var jobExperienceBuilders: List<JobExperienceBuilder> = listOf(JobExperienceBuilder())
    private var projectAndPublicationBuilders: List<ProjectOrPublicationBuilder> = listOf(
        ProjectOrPublicationBuilder()
    )
    private var degreeBuilders: List<DegreeBuilder> = listOf(DegreeBuilder())

    fun from(name: String) = builderMethod {
        this.name = name
    }

    fun withHeadline(headline: List<String>) = builderMethod {
        this.headline = headline
    }

    fun with(contactInformationBuilder: ContactInformationBuilder) = builderMethod {
        this.contactInformationBuilder = contactInformationBuilder
    }

    fun withEmptyHeadline() = withHeadline(emptyList())

    fun with(jobExperienceBuilders: List<JobExperienceBuilder>) = builderMethod {
        this.jobExperienceBuilders = jobExperienceBuilders
    }

    fun with(jobExperienceBuilder: JobExperienceBuilder) = builderMethod {
        this.jobExperienceBuilders = listOf(jobExperienceBuilder)
    }

    fun withNoExperience() = with(emptyList<JobExperienceBuilder>())

    fun append(jobExperienceBuilder: JobExperienceBuilder) = builderMethod {
        this.jobExperienceBuilders += jobExperienceBuilder
    }

    @JvmName("withProjectsAndPublications")
    fun with(projectAndPublicationBuilders: List<ProjectOrPublicationBuilder>) = builderMethod {
        this.projectAndPublicationBuilders = projectAndPublicationBuilders
    }

    fun with(projectsAndPublicationsBuilder: ProjectOrPublicationBuilder) = with(listOf(projectsAndPublicationsBuilder))

    fun withNoProjectsOrPublications() = with(emptyList<ProjectOrPublicationBuilder>())

    @JvmName("withDegress")
    fun with(degreeBuilders: List<DegreeBuilder>) = builderMethod {
        this.degreeBuilders = degreeBuilders
    }

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