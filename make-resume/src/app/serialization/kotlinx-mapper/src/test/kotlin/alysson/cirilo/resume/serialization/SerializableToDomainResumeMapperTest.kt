package alysson.cirilo.resume.serialization

import alysson.cirilo.resume.entities.EnrollmentPeriod
import alysson.cirilo.resume.entities.EnrollmentPeriod.EndDate.Past
import alysson.cirilo.resume.entities.EnrollmentPeriod.EndDate.Present
import alysson.cirilo.resume.entities.LinkedInformation
import alysson.cirilo.resume.entities.Resume
import alysson.cirilo.resume.entities.aBulletPoint
import alysson.cirilo.resume.serialization.models.SerializableEnrollmentPeriod
import alysson.cirilo.resume.serialization.models.SerializableLinkedInformation
import alysson.cirilo.resume.serialization.models.SerializableResume
import alysson.cirilo.resume.serialization.models.aJobExperienceDto
import alysson.cirilo.resume.serialization.models.aResumeDto
import alysson.cirilo.resume.serialization.models.aRoleDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.containExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.YearMonth

class SerializableToDomainResumeMapperTest {
    @Nested
    inner class ValidDto {
        private val validResumeDto = Fixtures.Valid.resume
        private val missingBracketResumeDto = Fixtures.Valid.missingOpenBracketResume

        private val resume by lazy { resume(validResumeDto) }

        @Test
        fun `should contain the name`() {
            resume.name shouldBe validResumeDto.name
        }

        @Test
        fun `should contain the headline`() {
            resume.headline should containExactly(validResumeDto.headline)
        }

        @Test
        fun `should contain email`() {
            resume.contactInformation.email shouldMatch validResumeDto.contactInfo.email
        }

        @Test
        fun `should contain linkedin`() {
            resume.contactInformation.linkedin shouldMatch validResumeDto.contactInfo.linkedin
        }

        @Test
        fun `should contain github`() {
            resume.contactInformation.github shouldMatch validResumeDto.contactInfo.github
        }

        @Test
        fun `should contain location`() {
            resume.contactInformation.location shouldMatch validResumeDto.contactInfo.location
        }

        @Test
        fun `should contain the same number of job experiences`() {
            resume.jobExperiences shouldHaveSize validResumeDto.experiences.size
        }

        @Test
        fun `should contain the company`() {
            val experience = resume.jobExperiences.first()

            experience.company shouldMatch validResumeDto.experiences.first().company
        }

        @Test
        fun `should contain experience location`() {
            val experience = resume.jobExperiences.first()

            experience.location shouldBe validResumeDto.experiences.first().location
        }

        @Test
        fun `should contain the same number of roles`() {
            val roles = resume.jobExperiences.first().roles

            roles shouldHaveSize validResumeDto.experiences.first().roles.size
        }

        @Test
        fun `should contain the role title`() {
            val roles = resume.jobExperiences.first().roles
            val rolesDto = validResumeDto.experiences.first().roles

            roles[0].title shouldBe rolesDto[0].title
            roles[1].title shouldBe rolesDto[1].title
        }

        @Test
        fun `should contain the role period`() {
            val roles = resume.jobExperiences.first().roles
            val rolesDto = validResumeDto.experiences.first().roles

            roles[0].period shouldMatch rolesDto[0].period
            roles[1].period shouldMatch rolesDto[1].period
        }

        @Test
        fun `should contain the same number of bullet points`() {
            val roles = resume.jobExperiences.first().roles
            val rolesDto = validResumeDto.experiences.first().roles

            roles[0].bulletPoints shouldHaveSize rolesDto[0].bulletPoints.size
            roles[1].bulletPoints shouldHaveSize rolesDto[1].bulletPoints.size
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
            val resume = resume(missingBracketResumeDto)
            val roles = resume.jobExperiences.first().roles

            roles[0].bulletPoints[0] shouldBe aBulletPoint()
                .containing("delivered value } with ", "kotlin", ".")
                .build()
        }

        @Test
        fun `should contain the same number of projects`() {
            val projects = resume.projectsAndPublications

            projects shouldHaveSize validResumeDto.projectsAndPublications.size
        }

        @Test
        fun `should contain the projects`() {
            val project = resume.projectsAndPublications.first()
            val projectDto = validResumeDto.projectsAndPublications.first()

            project.title shouldMatch projectDto.title
            project.description shouldBe projectDto.description
        }

        @Test
        fun `should contain the same number of degrees`() {
            val degrees = resume.education

            degrees shouldHaveSize 1
        }

        @Test
        fun `should contain the degrees`() {
            val degree = resume.education.first()
            val degreeDto = validResumeDto.education.first()

            degree.institution shouldMatch degreeDto.institution
            degree.location shouldBe degreeDto.location
            degree.degree shouldBe degreeDto.degree
            degree.period shouldMatch degreeDto.period
        }

        private fun resume(resumeDto: SerializableResume): Resume {
            return resumeDto.toDomain()
        }

        private infix fun LinkedInformation.shouldMatch(
            linkedInfoDto: SerializableLinkedInformation,
        ) {
            displayName shouldBe linkedInfoDto.displayName
            url.toString() shouldBe linkedInfoDto.url
        }

        private infix fun EnrollmentPeriod.shouldMatch(
            periodDto: SerializableEnrollmentPeriod,
        ) {
            fun parse(date: String): List<Int> {
                return date.split("-").map(String::toInt)
            }

            val (startMonth, startYear) = parse(periodDto.from)
            val start = YearMonth.of(startYear, startMonth)

            val end = if (periodDto.isCurrent) {
                Present
            } else {
                val (endMonth, endYear) = parse(periodDto.to)
                Past(endYear, endMonth)
            }

            this.start shouldBe start
            this.end shouldBe end
        }
    }

    @Nested
    inner class MalformedDto {
        @Test
        fun `missing closing bracket fails`() {
            shouldThrow<ParsingException> {
                Fixtures.Malformed.missingClosingBracketResume.toDomain()
            }
        }

        @Test
        fun `too many brackets fails`() {
            shouldThrow<ParsingException> {
                Fixtures.Malformed.tooManyBracketResume.toDomain()
            }
        }
    }
}

object Fixtures {

    object Valid {
        val resume = aResumeDto()
            .with(
                aJobExperienceDto()
                    .with(
                        aRoleDto().`as`("SWE 1").from("02-2022").upTo("12-2022")
                            .bullet("delivered value."),
                        aRoleDto().`as`("SWE 2").from("12-2022").upTo("now")
                            .bullets(
                                "delivered value with {kotlin}.",
                                "another cool thing",
                            ),
                    ),
            )
            .build()

        val missingOpenBracketResume = aResumeDto()
            .with(
                aJobExperienceDto()
                    .with(aRoleDto().bullet("delivered value } with {kotlin}.")),
            )
            .build()
    }

    object Malformed {
        val missingClosingBracketResume = aResumeDto()
            .with(
                aJobExperienceDto()
                    .with(aRoleDto().bullet("delivered value with {unclosed bracket.")),
            )
            .build()

        val tooManyBracketResume = aResumeDto()
            .with(
                aJobExperienceDto()
                    .with(aRoleDto().bullet("delivered value with {cool {technology}}.")),
            )
            .build()
    }
}
