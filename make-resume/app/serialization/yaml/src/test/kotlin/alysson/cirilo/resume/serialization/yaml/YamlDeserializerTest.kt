package alysson.cirilo.resume.serialization.yaml

import alysson.cirilo.resume.entities.EnrollmentPeriod.EndDate.Past
import alysson.cirilo.resume.entities.EnrollmentPeriod.EndDate.Present
import alysson.cirilo.resume.entities.Resume
import alysson.cirilo.resume.entities.aBulletPoint
import alysson.cirilo.resume.serialization.ParsingException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.containExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.YearMonth

class YamlDeserializerTest {
    @Nested
    inner class ValidJson {
        private val resume by lazy { resume("test-resume.yml") }

        @Test
        fun `should contain the name`() {
            resume.name shouldBe "First Last"
        }

        @Test
        fun `should contain the headline`() {
            resume.headline should containExactly("Software Engineer", "Android Developer")
        }

        @Test
        fun `should contain email`() {
            val email = resume.contactInformation.email

            email.displayName shouldBe "email"
            email.url.toString() shouldBe "https://www.email.com"
        }

        @Test
        fun `should contain linkedin`() {
            val linkedin = resume.contactInformation.linkedin
            linkedin.displayName shouldBe "linkedin"
            linkedin.url.toString() shouldBe "https://www.linkedin.com"
        }

        @Test
        fun `should contain github`() {
            val github = resume.contactInformation.github
            github.displayName shouldBe "github"
            github.url.toString() shouldBe "https://www.github.com"
        }

        @Test
        fun `should contain location`() {
            val location = resume.contactInformation.location
            location.displayName shouldBe "location"
            location.url.toString() shouldBe "https://www.gps.com"
        }

        @Test
        fun `should contain the same number of job experiences`() {
            val experiences = resume.jobExperiences
            experiences shouldHaveSize 1
        }

        @Test
        fun `should contain the company`() {
            val experience = resume.jobExperiences.first()
            experience.company.displayName shouldBe "Company"
            experience.company.url.toString() shouldBe "https://www.company.com"
        }

        @Test
        fun `should contain experience location`() {
            val experience = resume.jobExperiences.first()
            experience.location shouldBe "Remote"
        }

        @Test
        fun `should contain the same number of roles`() {
            val roles = resume.jobExperiences.first().roles
            roles shouldHaveSize 2
        }

        @Test
        fun `should contain the role title`() {
            val roles = resume.jobExperiences.first().roles
            roles[0].title shouldBe "SWE 1"
            roles[1].title shouldBe "SWE 2"
        }

        @Test
        fun `should contain the role period`() {
            val roles = resume.jobExperiences.first().roles

            roles[0].period.start shouldBe YearMonth.of(2022, 2)
            roles[0].period.end shouldBe Past(2022, 12)

            roles[1].period.start shouldBe YearMonth.of(2022, 12)
            roles[1].period.end shouldBe Present
        }

        @Test
        fun `should contain the same number of bullet points`() {
            val roles = resume.jobExperiences.first().roles

            roles[0].bulletPoints shouldHaveSize 1
            roles[1].bulletPoints shouldHaveSize 2
        }

        @Test
        fun `should contain the bullet points`() {
            val roles = resume.jobExperiences.first().roles

            roles[0].bulletPoints[0] shouldBe aBulletPoint().thatReads("delivered value.").build()

            roles[1].bulletPoints[0] shouldBe aBulletPoint()
                .containing("delivered value with ", "kotlin", ".")
                .build()
            roles[1].bulletPoints[1] shouldBe aBulletPoint().thatReads("another cool thing").build()
        }

        @Test
        fun `should handle unexpected closing bracket`() {
            val resume = resume("unexpected-closing-bracket.yml")
            val roles = resume.jobExperiences.first().roles

            roles[0].bulletPoints[0] shouldBe aBulletPoint()
                .containing("delivered value } with ", "kotlin", ".")
                .build()
        }

        @Test
        fun `should contain the same number of projects`() {
            val projects = resume.projectsAndPublications

            projects shouldHaveSize 1
        }

        @Test
        fun `should contain the projects`() {
            val project = resume.projectsAndPublications[0]

            project.title.displayName shouldBe "Project"
            project.title.url.toString() shouldBe "https://www.project.com"
            project.description shouldBe "This is a kotlin project"
        }

        @Test
        fun `should contain the same number of degrees`() {
            val degrees = resume.education

            degrees shouldHaveSize 1
        }

        @Test
        fun `should contain the degrees`() {
            val degree = resume.education[0]

            degree.institution.displayName shouldBe "Top institution"
            degree.institution.url.toString() shouldBe "https://www.topinstitution.edu"
            degree.location shouldBe "Brazil"
            degree.degree shouldBe "BSc. in Computer Science"
            degree.period.start shouldBe YearMonth.of(2018, 1)
            degree.period.end shouldBe Past(2022, 1)
        }

        private fun resume(path: String): Resume {
            val malformedJson = "/valid/$path".read()
            return deserializeYaml(malformedJson)
        }
    }

    @Nested
    inner class MalformedJson {
        @Test
        fun `missing closing bracket fails`() {
            shouldThrow<ParsingException> {
                malformedResume("missing-closing-bracket.yml")
            }
        }

        @Test
        fun `too many brackets fails`() {
            shouldThrow<ParsingException> {
                malformedResume("too-many-brackets.yml")
            }
        }

        private fun malformedResume(path: String): Resume {
            val malformedJson = "/malformed/$path".read()
            return deserializeYaml(malformedJson)
        }
    }

    private fun String.read(): String {
        return Unit.javaClass.getResource(this)!!.readText()
    }
}
