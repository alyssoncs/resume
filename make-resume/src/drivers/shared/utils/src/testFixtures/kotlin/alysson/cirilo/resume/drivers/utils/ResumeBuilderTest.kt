package alysson.cirilo.resume.drivers.utils

import alysson.cirilo.resume.drivers.utils.date.testEducationDateFormatter
import alysson.cirilo.resume.drivers.utils.date.testWorkDateFormatter
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.EnrollmentPeriod
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.JobExperienceBuilder
import alysson.cirilo.resume.entities.LinkedInformation
import alysson.cirilo.resume.entities.ProjectOrPublication
import alysson.cirilo.resume.entities.aBulletPoint
import alysson.cirilo.resume.entities.aCompany
import alysson.cirilo.resume.entities.aDegree
import alysson.cirilo.resume.entities.aJobExperience
import alysson.cirilo.resume.entities.aLinkedInfo
import alysson.cirilo.resume.entities.aProject
import alysson.cirilo.resume.entities.aRole
import alysson.cirilo.resume.entities.anEmptyBulletPoint
import alysson.cirilo.resume.entities.contactInfo
import alysson.cirilo.resume.entities.institution
import alysson.cirilo.resume.entities.period
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.YearMonth
import java.time.format.DateTimeFormatter

abstract class ResumeBuilderTest {

    lateinit var resumeBuilder: ResumeBuilder

    @BeforeEach
    fun setup() {
        resumeBuilder = createResumeBuilder(
            testWorkDateFormatter,
            testEducationDateFormatter,
        )
    }

    abstract fun createResumeBuilder(
        workDateFormatter: DateTimeFormatter,
        educationDateFormatter: DateTimeFormatter,
    ): ResumeBuilder

    abstract fun generateEmptyOutput(): String

    abstract fun generateSection(sectionName: String): String

    abstract fun generateHeader(
        name: String,
        firstHeadlineElement: String,
        secondHeadlineElement: String,
        contactInfo: ContactInformation,
    ): String

    abstract fun generateTwoExperiencesWithNoBullets(
        firstExperience: JobExperience,
        firstExperienceRole: String,
        firstExperienceRoleStartDate: String,
        firstExperienceRoleEndDate: String,
        secondExperience: JobExperience,
        secondExperienceRole: String,
        secondExperienceRoleStartDate: String,
    ): String

    abstract fun generateSingleRoleExperienceWithNoBullets(
        experience: JobExperience,
        role: String,
        roleStartDate: String,
        roleEndDate: String,
    ): String

    abstract fun generateSingleRoleExperienceWithSkillBullets(
        experience: JobExperience,
        role: String,
        roleStartDate: String,
        roleEndDate: String,
        firstBullet: String,
        secondBullet: String,
        thirdBulletFirstPart: String,
        thirdBulletSecondPart: String,
        thirdBulletThirdPart: String,
    ): String

    abstract fun generateTwoRoleExperienceWithBullets(
        company: LinkedInformation,
        location: String,
        firstRole: String,
        firstExperienceRoleStartDate: String,
        firstExperienceRoleEndDate: String,
        firstRoleBullet: String,
        secondRole: String,
        secondRoleStartDate: String,
        secondRoleBullet: String,
    ): String

    abstract fun generateSingleProject(project: ProjectOrPublication): String

    abstract fun generateSingleDegree(degree: Degree, startDate: String): String

    abstract fun generateIntegration(
        name: String,
        firstHeadlineElement: String,
        secondHeadlineElement: String,
        contactInfo: ContactInformation,
        firstSectionName: String,
        experience: JobExperience,
        role: String,
        roleStartDate: String,
        roleEndDate: String,
        bullet: String,
        secondSectionName: String,
        project: ProjectOrPublication,
        thirdSectionName: String,
        degree: Degree,
        degreeStartDate: String,
    ): String

    @Test
    fun `no content generates nothing`() {
        resumeBuilder.build() shouldBe generateEmptyOutput()
    }

    @Test
    fun `can generate a section`() {
        resumeBuilder.startSection("Section Name")

        resumeBuilder.build() shouldBe generateSection("Section Name")
    }

    @Test
    fun `can generate a header`() {
        resumeBuilder.addHeader(Dataset.name, Dataset.headline, Dataset.contactInfo)

        val header = generateHeader(
            Dataset.name,
            Dataset.headline.first(),
            Dataset.headline.last(),
            Dataset.contactInfo,
        )
        resumeBuilder.build() shouldBe header
    }

    @Test
    fun `no job experiences generates nothing`() {
        resumeBuilder.makeExperiences(Dataset.noExperience)

        resumeBuilder.build() shouldBe generateEmptyOutput()
    }

    @Test
    fun `can generate job experiences`() {
        resumeBuilder.makeExperiences(Dataset.twoExperiencesWithNoBullets)

        val firstExperienceRole = Dataset.twoExperiencesWithNoBullets.first().roles.first()
        val secondExperienceRole = Dataset.twoExperiencesWithNoBullets.last().roles.first()
        val output = generateTwoExperiencesWithNoBullets(
            firstExperience = Dataset.twoExperiencesWithNoBullets.first(),
            firstExperienceRole = firstExperienceRole.title,
            firstExperienceRoleStartDate = testWorkDateFormatter.format(firstExperienceRole.period.start),
            firstExperienceRoleEndDate = testWorkDateFormatter.format(firstExperienceRole.period.end.toDate()),
            secondExperience = Dataset.twoExperiencesWithNoBullets.last(),
            secondExperienceRole = secondExperienceRole.title,
            secondExperienceRoleStartDate = testWorkDateFormatter.format(secondExperienceRole.period.start),
        )
        resumeBuilder.build() shouldBe output
    }

    @Test
    fun `can generate single role`() {
        resumeBuilder.makeExperiences(Dataset.singleRoleExperienceWithNoBullets)

        val role = Dataset.singleRoleExperienceWithNoBullets.first().roles.first()
        val output = generateSingleRoleExperienceWithNoBullets(
            experience = Dataset.singleRoleExperienceWithNoBullets.first(),
            role = role.title,
            roleStartDate = testWorkDateFormatter.format(role.period.start),
            roleEndDate = testWorkDateFormatter.format(role.period.end.toDate()),
        )
        resumeBuilder.build() shouldBe output
    }

    @Test
    fun `can generate role with bullets`() {
        resumeBuilder.makeExperiences(Dataset.singleRoleExperienceWithSkillBullets)

        val experience = Dataset.singleRoleExperienceWithSkillBullets.first()
        val role = experience.roles.first()
        val output = generateSingleRoleExperienceWithSkillBullets(
            experience = experience,
            role = role.title,
            roleStartDate = testWorkDateFormatter.format(role.period.start),
            roleEndDate = testWorkDateFormatter.format(role.period.end.toDate()),
            firstBullet = role.bulletPoints[0].content[0].displayName,
            secondBullet = role.bulletPoints[1].content[0].displayName,
            thirdBulletFirstPart = role.bulletPoints[2].content[0].displayName,
            thirdBulletSecondPart = role.bulletPoints[2].content[1].displayName,
            thirdBulletThirdPart = role.bulletPoints[2].content[2].displayName,
        )
        resumeBuilder.build() shouldBe output
    }

    @Test
    fun `can generate multiple roles`() {
        resumeBuilder.makeExperiences(Dataset.twoRoleExperienceWithBullets)

        val experience = Dataset.twoRoleExperienceWithBullets.first()
        val firstRole = experience.roles.first()
        val secondRole = experience.roles.last()
        val output = generateTwoRoleExperienceWithBullets(
            company = experience.company,
            location = experience.location,
            firstRole = firstRole.title,
            firstExperienceRoleStartDate = testWorkDateFormatter.format(firstRole.period.start),
            firstExperienceRoleEndDate = testWorkDateFormatter.format(firstRole.period.end.toDate()),
            firstRoleBullet = firstRole.bulletPoints.first().content.first().displayName,
            secondRole = secondRole.title,
            secondRoleStartDate = testWorkDateFormatter.format(secondRole.period.start),
            secondRoleBullet = secondRole.bulletPoints.first().content.first().displayName,
        )
        resumeBuilder.build() shouldBe output
    }

    @Test
    fun `no projects and publications generates nothing`() {
        resumeBuilder.makeProjectsAndPublications(Dataset.noProjectsOrPublications)

        resumeBuilder.build() shouldBe generateEmptyOutput()
    }

    @Test
    fun `can generate project and publications`() {
        resumeBuilder.makeProjectsAndPublications(Dataset.singleProject)

        val output = generateSingleProject(Dataset.singleProject.first())
        resumeBuilder.build() shouldBe output
    }

    @Test
    fun `no education generates nothing`() {
        resumeBuilder.makeEducation(Dataset.noEducation)

        resumeBuilder.build() shouldBe generateEmptyOutput()
    }

    @Test
    fun `can generate education`() {
        resumeBuilder.makeEducation(Dataset.singleDegree)

        val degree = Dataset.singleDegree.first()
        resumeBuilder.build() shouldBe generateSingleDegree(
            degree,
            testEducationDateFormatter.format(degree.period.start),
        )
    }

    @Test
    fun integration() {
        with(resumeBuilder) {
            addHeader(Dataset.name, Dataset.headline, Dataset.contactInfo)
            startSection("Section 1")
            makeExperiences(Dataset.singleRoleExperienceWithTextBullet)
            startSection("Section 2")
            makeProjectsAndPublications(Dataset.singleProject)
            startSection("Section 3")
            makeEducation(Dataset.singleDegree)
        }

        val experience = Dataset.singleRoleExperienceWithTextBullet.first()
        val role = experience.roles.first()
        val output = generateIntegration(
            name = Dataset.name,
            firstHeadlineElement = Dataset.headline.first(),
            secondHeadlineElement = Dataset.headline.last(),
            contactInfo = Dataset.contactInfo,
            firstSectionName = "Section 1",
            experience = experience,
            role = role.title,
            roleStartDate = testWorkDateFormatter.format(role.period.start),
            roleEndDate = testWorkDateFormatter.format(role.period.end.toDate()),
            bullet = role.bulletPoints.first().content.first().displayName,
            secondSectionName = "Section 2",
            project = Dataset.singleProject.first(),
            thirdSectionName = "Section 3",
            degree = Dataset.singleDegree.first(),
            degreeStartDate = testEducationDateFormatter.format(Dataset.singleDegree.first().period.start),
        )
        resumeBuilder.build() shouldBe output
    }

    private fun EnrollmentPeriod.EndDate.toDate(): YearMonth {
        return (this as EnrollmentPeriod.EndDate.Past).date
    }

    companion object {

        object Dataset {
            const val name: String = "First Last"

            val headline: List<String> = listOf("Software Engineer", "Android Developer")

            val contactInfo: ContactInformation = contactInfo()
                .email(
                    aLinkedInfo()
                        .displaying("email")
                        .linkingTo("https://www.email.com"),
                )
                .linkedin(
                    aLinkedInfo()
                        .displaying("linkedin")
                        .linkingTo("https://www.linkedin.com"),
                )
                .github(
                    aLinkedInfo()
                        .displaying("github")
                        .linkingTo("https://www.github.com"),
                )
                .location(
                    aLinkedInfo()
                        .displaying("location")
                        .linkingTo("https://www.gps.com"),
                )
                .build()

            val noExperience: List<JobExperience> = emptyList()

            val twoExperiencesWithNoBullets: List<JobExperience> = buildList {
                add(Builders.jobExperienceBuilder.with(Builders.role.withNoBulletPoints()))
                add(
                    aJobExperience()
                        .on(aCompany("Company 2", "https://www.company.com"))
                        .basedOn("Remote")
                        .with(
                            aRole()
                                .`as`("SWE 2")
                                .withNoBulletPoints()
                                .between(period().from(month = 12, year = 2019).upToNow()),
                        ),
                )
            }.map(JobExperienceBuilder::build)

            val singleRoleExperienceWithNoBullets: List<JobExperience> = listOf(
                Builders.jobExperienceBuilder.with(Builders.role.withNoBulletPoints()).build(),
            )

            val singleRoleExperienceWithSkillBullets: List<JobExperience> = listOf(
                Builders.jobExperienceBuilder
                    .with(
                        Builders.role
                            .with(aBulletPoint().thatReads("delivered value"))
                            .and(aBulletPoint().withSkill("android"))
                            .and(
                                anEmptyBulletPoint()
                                    .appendText("delivered value with ")
                                    .appendSkill("kotlin")
                                    .appendText(" but at what cost?"),
                            ),
                    )
                    .build(),
            )

            val twoRoleExperienceWithBullets: List<JobExperience> = listOf(
                Builders.jobExperienceBuilder
                    .and(
                        aRole()
                            .`as`("SWE 2")
                            .between(period().from(month = 12, year = 2019).upToNow())
                            .with(aBulletPoint().thatReads("been promoted")),
                    )
                    .build(),
            )

            val singleRoleExperienceWithTextBullet = listOf(Builders.jobExperienceBuilder.build())

            val noProjectsOrPublications: List<ProjectOrPublication> = emptyList()

            val singleProject: List<ProjectOrPublication> = listOf(
                aProject()
                    .named("Project X")
                    .hostedOn("https://www.projectx.com")
                    .description("Project X description")
                    .build(),
            )

            val noEducation: List<Degree> = emptyList()

            val singleDegree: List<Degree> = listOf(
                aDegree()
                    .at(institution("Top institution", "https://www.topinstitution.edu"))
                    .on("Brazil")
                    .during(period().from(month = 2, year = 2022).upToNow())
                    .tile("BSc. in Computer Science")
                    .build(),
            )
        }

        private object Builders {
            val role = aRole()
                .`as`("SWE 1")
                .between(period().from(month = 10, year = 2019).to(month = 12, year = 2019))
                .with(aBulletPoint().thatReads("worked with kotlin"))

            val jobExperienceBuilder = aJobExperience()
                .on(aCompany("Company 1", "https://www.company.com"))
                .basedOn("Remote")
                .with(role)
        }
    }
}
