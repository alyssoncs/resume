package alysson.cirilo.resume.drivers.test

import alysson.cirilo.resume.drivers.utils.syntaxfactory.ResumeSyntaxFactory
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.LinkedInformation
import alysson.cirilo.resume.entities.ProjectOrPublication
import alysson.cirilo.resume.entities.test.databuilders.JobExperienceBuilder
import alysson.cirilo.resume.entities.test.databuilders.aBulletPoint
import alysson.cirilo.resume.entities.test.databuilders.aCompany
import alysson.cirilo.resume.entities.test.databuilders.aDegree
import alysson.cirilo.resume.entities.test.databuilders.aJobExperience
import alysson.cirilo.resume.entities.test.databuilders.aLinkedInfo
import alysson.cirilo.resume.entities.test.databuilders.aProject
import alysson.cirilo.resume.entities.test.databuilders.aRole
import alysson.cirilo.resume.entities.test.databuilders.anEmptyBulletPoint
import alysson.cirilo.resume.entities.test.databuilders.contactInfo
import alysson.cirilo.resume.entities.test.databuilders.institution
import alysson.cirilo.resume.entities.test.databuilders.period
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

abstract class ResumeSyntaxFactoryTest {

    lateinit var syntaxFactory: ResumeSyntaxFactory
    
    @BeforeEach
    fun setup() {
        syntaxFactory = createSyntaxFactory()
    }

    abstract fun createSyntaxFactory(): ResumeSyntaxFactory
    
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
        secondExperience: JobExperience,
        secondExperienceRole: String,
    ): String

    abstract fun generateSingleRoleExperienceWithNoBullets(
        experience: JobExperience,
        role: String,
    ): String

    abstract fun generateSingleRoleExperienceWithSkillBullets(
        experience: JobExperience,
        role: String,
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
        firstRoleBullet: String,
        secondRole: String,
        secondRoleBullet: String,
    ): String

    abstract fun generateSingleProject(project: ProjectOrPublication): String

    abstract fun generateSingleDegree(degree: Degree): String

    abstract fun generateIntegration(
        name: String,
        firstHeadlineElement: String,
        secondHeadlineElement: String,
        contactInfo: ContactInformation,
        firstSectionName: String,
        experience: JobExperience,
        role: String,
        bullet: String,
        secondSectionName: String,
        project: ProjectOrPublication,
        thirdSectionName: String,
        degree: Degree,
    ): String

    @Test
    fun `no content generates nothing`() {
        assertEquals(generateEmptyOutput(), syntaxFactory.create())
    }

    @Test
    fun `can generate a section`() {
        syntaxFactory.startSection("Section Name")

        assertEquals(generateSection("Section Name"), syntaxFactory.create())
    }

    @Test
    fun `can generate a header`() {
        syntaxFactory.addHeader(Dataset.name, Dataset.headline, Dataset.contactInfo)

        val header = generateHeader(
            Dataset.name,
            Dataset.headline.first(),
            Dataset.headline.last(),
            Dataset.contactInfo,
        )
        assertEquals(header, syntaxFactory.create())
    }

    @Test
    fun `no job experiences generates nothing`() {
        syntaxFactory.makeExperiences(Dataset.noExperience)

        assertEquals(generateEmptyOutput(), syntaxFactory.create())
    }

    @Test
    fun `can generate job experiences`() {
        syntaxFactory.makeExperiences(Dataset.twoExperiencesWithNoBullets)

        val output = generateTwoExperiencesWithNoBullets(
            firstExperience = Dataset.twoExperiencesWithNoBullets.first(),
            firstExperienceRole = Dataset.twoExperiencesWithNoBullets.first().roles.first().title,
            secondExperience = Dataset.twoExperiencesWithNoBullets.last(),
            secondExperienceRole = Dataset.twoExperiencesWithNoBullets.last().roles.first().title,
        )
        assertEquals(output, syntaxFactory.create())
    }

    @Test
    fun `can generate single role`() {
        syntaxFactory.makeExperiences(Dataset.singleRoleExperienceWithNoBullets)

        val output = generateSingleRoleExperienceWithNoBullets(
            experience = Dataset.singleRoleExperienceWithNoBullets.first(),
            role = Dataset.singleRoleExperienceWithNoBullets.first().roles.first().title,
        )
        assertEquals(output, syntaxFactory.create())
    }

    @Test
    fun `can generate role with bullets`() {
        syntaxFactory.makeExperiences(Dataset.singleRoleExperienceWithSkillBullets)

        val experience = Dataset.singleRoleExperienceWithSkillBullets.first()
        val role = experience.roles.first()
        val output = generateSingleRoleExperienceWithSkillBullets(
            experience = experience,
            role = role.title,
            firstBullet = role.bulletPoints[0].content[0].displayName,
            secondBullet = role.bulletPoints[1].content[0].displayName,
            thirdBulletFirstPart = role.bulletPoints[2].content[0].displayName,
            thirdBulletSecondPart = role.bulletPoints[2].content[1].displayName,
            thirdBulletThirdPart = role.bulletPoints[2].content[2].displayName,
        )
        assertEquals(output, syntaxFactory.create())
    }


    @Test
    fun `can generate multiple roles`() {
        syntaxFactory.makeExperiences(Dataset.twoRoleExperienceWithBullets)

        val experience = Dataset.twoRoleExperienceWithBullets.first()
        val firstRole = experience.roles.first()
        val secondRole = experience.roles.last()
        val output = generateTwoRoleExperienceWithBullets(
            company = experience.company,
            location = experience.location,
            firstRole = firstRole.title,
            firstRoleBullet = firstRole.bulletPoints.first().content.first().displayName,
            secondRole = secondRole.title,
            secondRoleBullet = secondRole.bulletPoints.first().content.first().displayName,
        )
        assertEquals(output, syntaxFactory.create())
    }

    @Test
    fun `no projects and publications generates nothing`() {
        syntaxFactory.makeProjectsAndPublications(Dataset.noProjectsOrPublications)

        assertEquals(generateEmptyOutput(), syntaxFactory.create())
    }

    @Test
    fun `can generate project and publications`() {
        syntaxFactory.makeProjectsAndPublications(Dataset.singleProject)

        val output = generateSingleProject(Dataset.singleProject.first())
        assertEquals(output, syntaxFactory.create())
    }

    @Test
    fun `no education generates nothing`() {
        syntaxFactory.makeEducation(Dataset.noEducation)

        assertEquals(generateEmptyOutput(), syntaxFactory.create())
    }

    @Test
    fun `can generate education`() {
        syntaxFactory.makeEducation(Dataset.singleDegree)

        assertEquals(
            generateSingleDegree(Dataset.singleDegree.first()),
            syntaxFactory.create()
        )
    }

    @Test
    fun integration() {
        with(syntaxFactory) {
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
            bullet = role.bulletPoints.first().content.first().displayName,
            secondSectionName = "Section 2",
            project = Dataset.singleProject.first(),
            thirdSectionName = "Section 3",
            degree = Dataset.singleDegree.first(),
        )
        assertEquals(output, syntaxFactory.create())
    }

    companion object {

        object Dataset {
            const val name: String = "First Last"

            val headline: List<String> = listOf("Software Engineer", "Android Developer")

            val contactInfo: ContactInformation = contactInfo()
                .email(
                    aLinkedInfo()
                        .displaying("email")
                        .linkingTo("https://www.email.com")
                )
                .linkedin(
                    aLinkedInfo()
                        .displaying("linkedin")
                        .linkingTo("https://www.linkedin.com")
                )
                .github(
                    aLinkedInfo()
                        .displaying("github")
                        .linkingTo("https://www.github.com")
                )
                .location(
                    aLinkedInfo()
                        .displaying("location")
                        .linkingTo("https://www.gps.com")
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
                                .between(period().from(12, 2019).upToNow())
                        )
                )
            }.map(JobExperienceBuilder::build)

            val singleRoleExperienceWithNoBullets: List<JobExperience> = listOf(
                Builders.jobExperienceBuilder.with(Builders.role.withNoBulletPoints()).build()
            )

            val singleRoleExperienceWithSkillBullets: List<JobExperience> = listOf(
                Builders.jobExperienceBuilder
                    .with(
                        Builders.role.withNoBulletPoints()
                            .append(aBulletPoint().thatReads("delivered value"))
                            .append(aBulletPoint().withSkill("android"))
                            .append(
                                anEmptyBulletPoint()
                                    .appendText("delivered value with ")
                                    .appendSkill("kotlin")
                                    .appendText(" but at what cost?")
                            )
                    )
                    .build()
            )

            val twoRoleExperienceWithBullets: List<JobExperience> = listOf(
                Builders.jobExperienceBuilder
                    .append(
                        aRole()
                            .`as`("SWE 2")
                            .between(period().from(12, 2019).upToNow())
                            .with(aBulletPoint().thatReads("been promoted"))
                    )
                    .build()
            )

            val singleRoleExperienceWithTextBullet = listOf(Builders.jobExperienceBuilder.build())

            val noProjectsOrPublications: List<ProjectOrPublication> = emptyList()

            val singleProject: List<ProjectOrPublication> = listOf(
                aProject()
                    .named("Project X")
                    .hostedOn("https://www.projectx.com")
                    .description("Project X description")
                    .build()
            )

            val noEducation: List<Degree> = emptyList()

            val singleDegree: List<Degree> = listOf(
                aDegree()
                    .at(institution("Top institution", "https://www.topinstitution.edu"))
                    .on("Brazil")
                    .during(period().from(2, 2022).upToNow())
                    .tile("BSc. in Computer Science")
                    .build()
            )
        }

        private object Builders {
            val role = aRole()
                .`as`("SWE 1")
                .between(period().from(10, 2019).to(12, 2019))
                .with(aBulletPoint().thatReads("worked with kotlin"))

            val jobExperienceBuilder = aJobExperience()
                .on(aCompany("Company 1", "https://www.company.com"))
                .basedOn("Remote")
                .with(role)
        }
    }
}
