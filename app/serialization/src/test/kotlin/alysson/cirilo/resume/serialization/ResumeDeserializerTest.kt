package alysson.cirilo.resume.serialization

import alysson.cirilo.resume.entities.BulletPoint
import alysson.cirilo.resume.entities.EnrollmentPeriod.EndDate.Past
import alysson.cirilo.resume.entities.EnrollmentPeriod.EndDate.Present
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ResumeDeserializerTest {
    private val jsonResume: String = "/test-resume.json".asResource()
    private val resume by lazy { deserialize(jsonResume) }

    @Test
    fun `should contain the name`() {
        assertThat(resume.name).isEqualTo("First Last")
    }

    @Test
    fun `should contain the headline`() {
        assertThat(resume.headline)
            .containsExactly("Software Engineer", "Android Developer")
            .inOrder()
    }

    @Test
    fun `should contain email`() {
        val email = resume.contactInformation.email
        assertThat(email.displayName).isEqualTo("email")
        assertThat(email.url.toString()).isEqualTo("https://www.email.com")
    }

    @Test
    fun `should contain linkedin`() {
        val linkedin = resume.contactInformation.linkedin
        assertThat(linkedin.displayName).isEqualTo("linkedin")
        assertThat(linkedin.url.toString()).isEqualTo("https://www.linkedin.com")
    }

    @Test
    fun `should contain github`() {
        val github = resume.contactInformation.github
        assertThat(github.displayName).isEqualTo("github")
        assertThat(github.url.toString()).isEqualTo("https://www.github.com")
    }

    @Test
    fun `should contain location`() {
        val location = resume.contactInformation.location
        assertThat(location.displayName).isEqualTo("location")
        assertThat(location.url.toString()).isEqualTo("https://www.gps.com")
    }

    @Test
    fun `should contain the same number of job experiences`() {
        val experiences = resume.jobExperiences
        assertThat(experiences).hasSize(1)
    }

    @Test
    fun `should contain the company`() {
        val experience = resume.jobExperiences.first()
        assertThat(experience.company.displayName).isEqualTo("Company")
        assertThat(experience.company.url.toString()).isEqualTo("https://www.company.com")
    }

    @Test
    fun `should contain experience location`() {
        val experience = resume.jobExperiences.first()
        assertThat(experience.location).isEqualTo("Remote")
    }

    @Test
    fun `should contain the same number of roles`() {
        val roles = resume.jobExperiences.first().roles
        assertThat(roles).hasSize(2)
    }

    @Test
    fun `should contain the role title`() {
        val roles = resume.jobExperiences.first().roles
        assertThat(roles[0].title).isEqualTo("SWE 1")
        assertThat(roles[1].title).isEqualTo("SWE 2")
    }

    @Test
    fun `should contain the role period`() {
        val roles = resume.jobExperiences.first().roles

        assertThat(roles[0].period.start).isEqualTo(LocalDate.of(2022, 2, 1))
        assertThat(roles[0].period.end).isEqualTo(Past(LocalDate.of(2022, 12, 1)))

        assertThat(roles[1].period.start).isEqualTo(LocalDate.of(2022, 12, 1))
        assertThat(roles[1].period.end).isEqualTo(Present)
    }

    @Test
    fun `should contain the same number of bullet points`() {
        val roles = resume.jobExperiences.first().roles

        assertThat(roles[0].bulletPoints).hasSize(1)
        assertThat(roles[1].bulletPoints).hasSize(2)
    }

    @Test
    fun `should contain the bullet points`() {
        val roles = resume.jobExperiences.first().roles

        assertThat(roles[0].bulletPoints[0])
            .isEqualTo(BulletPoint("delivered value."))

        assertThat(roles[1].bulletPoints[0])
            .isEqualTo(BulletPoint("delivered value with ", "kotlin", "."))
        assertThat(roles[1].bulletPoints[1])
            .isEqualTo(BulletPoint("another cool thing"))
    }

    @Test
    fun `should contain the same number of projects`() {
        val projects = resume.projectsAndPublications

        assertThat(projects).hasSize(1)
    }

    @Test
    fun `should contain the projects`() {
        val project = resume.projectsAndPublications[0]

        assertThat(project.title.displayName).isEqualTo("Project")
        assertThat(project.title.url.toString()).isEqualTo("https://www.project.com")
        assertThat(project.description).isEqualTo("This is a kotlin project")
    }

    @Test
    fun `should contain the same number of degrees`() {
        val degrees = resume.education

        assertThat(degrees).hasSize(1)
    }

    @Test
    fun `should contain the degrees`() {
        val degree = resume.education[0]

        assertThat(degree.institution.displayName).isEqualTo("Top institution")
        assertThat(degree.institution.url.toString()).isEqualTo("https://www.topinstitution.edu")
        assertThat(degree.location).isEqualTo("Brazil")
        assertThat(degree.degree).isEqualTo("BSc. in Computer Science")
        assertThat(degree.period.start).isEqualTo(LocalDate.of(2018, 1, 1))
        assertThat(degree.period.end).isEqualTo(Past(LocalDate.of(2022, 1, 1)))
    }

    private fun String.asResource(): String {
        return object {}.javaClass.getResource(this)!!.readText()
    }
}