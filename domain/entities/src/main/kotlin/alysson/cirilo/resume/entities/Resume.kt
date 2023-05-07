package alysson.cirilo.resume.entities

data class Resume(
    val name: String,
    val headline: List<String>,
    val contactInformation: ContactInformation,
    val jobExperiences: List<JobExperience>,
    val projectsAndPublications: List<ProjectOrPublication>,
    val education: List<Degree>,
) {
    init {
        require(name.isNotBlank()) { "Resume name cannot be blank" }
        require(headline.isNotEmpty()) { "Resume headline cannot be empty" }
    }

    val skills: List<ProfessionalSkill> by lazy {
        jobExperiences.flatMap { jobExperience ->
            jobExperience.roles.flatMap { role ->
                role.bulletPoints.flatMap { bulletPoint ->
                    bulletPoint.content.filterIsInstance<BulletPointContent.Skill>().map {
                        it.skill
                    }
                }
            }
        }
    }
}
