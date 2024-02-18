package alysson.cirilo.resume.entities

data class Resume(
    val name: String,
    val headline: List<String>,
    val contactInformation: ContactInformation,
    val jobExperiences: List<JobExperience>,
    val projectsAndPublications: List<ProjectOrPublication>,
    val education: List<Degree>,
) {
    val reversedChronologically: Resume by lazy {
        this.copy(
            jobExperiences = this.jobExperiences.reversed().map { jobExperience ->
                jobExperience.copy(roles = jobExperience.roles.reversed())
            },
        )
    }

    init {
        require(name.isNotBlank()) { "Resume name cannot be blank" }
        require(headline.isNotEmpty()) { "Resume headline cannot be empty" }
    }

    val skills: Set<ProfessionalSkill> by lazy {
        jobExperiences.flatMap { jobExperience ->
            jobExperience.roles.flatMap { role ->
                role.bulletPoints.flatMap { bulletPoint ->
                    bulletPoint.content.filterIsInstance<BulletPointContent.Skill>().map(BulletPointContent.Skill::skill)
                }
            }
        }.toSet()
    }
}
