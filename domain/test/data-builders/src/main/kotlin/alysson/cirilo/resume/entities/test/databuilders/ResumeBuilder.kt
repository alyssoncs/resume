package alysson.cirilo.resume.entities.test.databuilders

import alysson.cirilo.resume.entities.Resume

class ResumeBuilder {
    private var name: String = "Alysson Cirilo"
    private var headline: List<String> = listOf("Android Developer", "Software Engineer")
    private var contactInformationBuilder: ContactInformationBuilder = ContactInformationBuilder()
    private var jobExperienceBuilders: List<JobExperienceBuilder> = listOf(JobExperienceBuilder())
    private var projectsAndPublicationBuilders: List<ProjectOrPublicationBuilder> = listOf(
        ProjectOrPublicationBuilder()
    )
    private val degreeBuilders: List<DegreeBuilder> = listOf(DegreeBuilder())

    fun withName(name: String) = builderMethod {
        this.name = name
    }

    fun withHeadline(headline: List<String>) = builderMethod {
        this.headline = headline
    }

    fun withEmptyHeadline() = withHeadline(emptyList())

    fun with(jobExperienceBuilders: List<JobExperienceBuilder>) = builderMethod {
        this.jobExperienceBuilders = jobExperienceBuilders
    }

    fun with(jobExperienceBuilder: JobExperienceBuilder) = builderMethod {
        this.jobExperienceBuilders = listOf(jobExperienceBuilder)
    }

    fun withNoExperience() = with(emptyList())

    fun append(jobExperienceBuilder: JobExperienceBuilder) = builderMethod {
        this.jobExperienceBuilders += jobExperienceBuilder
    }

    fun build(): Resume {
        return Resume(
            name = name,
            headline = headline,
            contactInformation = contactInformationBuilder.build(),
            jobExperiences = jobExperienceBuilders.map(JobExperienceBuilder::build),
            projectsAndPublications = projectsAndPublicationBuilders.map(ProjectOrPublicationBuilder::build),
            education = degreeBuilders.map(DegreeBuilder::build),
        )
    }

}

fun aResume(): ResumeBuilder = ResumeBuilder()