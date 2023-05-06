package alysson.cirilo.resume.entities

data class Resume(
    val name: String,
    val titles: List<String>,
    val contactInformation: ContactInformation,
    val jobExperiences: List<JobExperience>,
    val projectsAndPublications: List<ProjectOrPublication>,
    val education: List<Education>,
)