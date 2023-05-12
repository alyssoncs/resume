package alysson.cirilo.resume.drivers

import alysson.cirilo.resume.entities.test.databuilders.ResumeBuilder
import alysson.cirilo.resume.entities.test.databuilders.aBulletPoint
import alysson.cirilo.resume.entities.test.databuilders.aCompany
import alysson.cirilo.resume.entities.test.databuilders.aDegree
import alysson.cirilo.resume.entities.test.databuilders.aJobExperience
import alysson.cirilo.resume.entities.test.databuilders.aLinkedInfo
import alysson.cirilo.resume.entities.test.databuilders.aProject
import alysson.cirilo.resume.entities.test.databuilders.aResume
import alysson.cirilo.resume.entities.test.databuilders.aRole
import alysson.cirilo.resume.entities.test.databuilders.anEmptyBulletPoint
import alysson.cirilo.resume.entities.test.databuilders.contactInfo
import alysson.cirilo.resume.entities.test.databuilders.institution
import alysson.cirilo.resume.entities.test.databuilders.period
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class MarkdownResumeTest {
    private val markdownResume = MarkdownResume()

    private val role = aRole()
        .`as`("SWE 1")
        .between(period().from(10, 2019).to(12, 2019))
        .with(aBulletPoint().thatReads("worked with kotlin"))

    private val jobExperience = aJobExperience()
        .on(aCompany("Company 1", "https://www.company.com"))
        .basedOn("Remote")
        .with(role)

    private val resume = aResume()
        .from("First Last")
        .withHeadline(listOf("Software Engineer", "Android Developer"))
        .with(
            contactInfo()
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
        )
        .with(jobExperience)
        .with(
            aProject()
                .named("Project X")
                .hostedOn("https://www.projectx.com")
                .description("Project X description")
        )
        .with(
            aDegree()
                .at(institution("Top institution", "https://www.topinstitution.edu"))
                .on("Brazil")
                .during(period().from(2, 2022).upToNow())
                .tile("BSc. in Computer Science")
        )

    @Test
    fun `no content generates nothing`() {
        assertThat(markdownResume.toString()).isEqualTo("\n")
    }

    @Test
    fun `can generate a section`() {
        markdownResume.startSection("Section Name")

        assertThat(markdownResume.toString()).isEqualTo("## Section Name\n")
    }

    @Test
    fun `a header is composed correctly`() {
        markdownResume.addHeader(resume)

        assertThat(markdownResume.toString()).isEqualTo(
            """
                # First Last
                > Software Engineer • Android Developer
                
                ## Contact Information
                - [email](https://www.email.com)
                - [linkedin](https://www.linkedin.com)
                - [github](https://www.github.com)
                - [location](https://www.gps.com)
                
            """.trimIndent()
        )
    }

    @Test
    fun `no job experiences generates nothing`() {
        markdownResume.makeExperiences(resume.withNoExperience())

        assertThat(markdownResume.toString()).isEqualTo("\n")
    }

    @Test
    fun `can generate job experiences`() {
        val resume = resume.withNoExperience()
            .append(jobExperience.with(role.withNoBulletPoints()))
            .append(
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

        markdownResume.makeExperiences(resume)

        assertThat(markdownResume.toString()).isEqualTo(
            """
                ### [Company 1](https://www.company.com)
                - Remote
                
                #### SWE 1
                > Oct. 2019 – Dec. 2019
                
                ### [Company 2](https://www.company.com)
                - Remote
                
                #### SWE 2
                > Dec. 2019 – Present
                
            """.trimIndent()
        )
    }

    @Test
    fun `can generate single role`() {
        val resume = resume
            .with(jobExperience.with(role.withNoBulletPoints()))

        markdownResume.makeExperiences(resume)

        assertThat(markdownResume.toString()).isEqualTo(
            """
                ### [Company 1](https://www.company.com)
                - Remote
                
                #### SWE 1
                > Oct. 2019 – Dec. 2019
                
            """.trimIndent()
        )
    }

    @Test
    fun `can generate role with bullets`() {
        val resume = resume
            .with(
                jobExperience
                    .with(
                        role.withNoBulletPoints()
                            .append(aBulletPoint().thatReads("delivered value"))
                            .append(aBulletPoint().withSkill("android"))
                            .append(
                                anEmptyBulletPoint()
                                    .appendText("delivered value with ")
                                    .appendSkill("kotlin")
                                    .appendText(" but at what cost?")
                            )
                    )
            )

        markdownResume.makeExperiences(resume)

        assertThat(markdownResume.toString()).isEqualTo(
            """
                ### [Company 1](https://www.company.com)
                - Remote
                
                #### SWE 1
                > Oct. 2019 – Dec. 2019
                - delivered value
                - **android**
                - delivered value with **kotlin** but at what cost?
                
            """.trimIndent()
        )
    }

    @Test
    fun `can generate multiple roles`() {
        val resume = resume
            .with(
                jobExperience
                    .append(
                        aRole()
                            .`as`("SWE 2")
                            .between(period().from(12, 2019).upToNow())
                            .with(aBulletPoint().thatReads("been promoted"))
                    )
            )

        markdownResume.makeExperiences(resume)

        assertThat(markdownResume.toString()).isEqualTo(
            """
                ### [Company 1](https://www.company.com)
                - Remote
                
                #### SWE 1
                > Oct. 2019 – Dec. 2019
                - worked with kotlin
                
                #### SWE 2
                > Dec. 2019 – Present
                - been promoted
                
            """.trimIndent()
        )
    }

    @Test
    fun `no projects and publications generates nothing`() {
        markdownResume.makeProjectsAndPublications(resume.withNoProjectsOrPublications())

        assertThat(markdownResume.toString()).isEqualTo("\n")
    }

    @Test
    fun `can generate project and publications`() {
        markdownResume.makeProjectsAndPublications(resume)

        assertThat(markdownResume.toString()).isEqualTo(
            """
                - **[Project X](https://www.projectx.com)**: Project X description
                
            """.trimIndent()
        )
    }

    @Test
    fun `no education generates nothing`() {
        markdownResume.makeEducation(resume.withNoEducation())

        assertThat(markdownResume.toString()).isEqualTo("\n")
    }

    @Test
    fun `can generate education`() {
        markdownResume.makeEducation(resume)

        assertThat(markdownResume.toString()).isEqualTo(
            """
                - **[Top institution](https://www.topinstitution.edu)**: BSc. in Computer Science (Brazil 2022 – Present)
                
            """.trimIndent()
        )
    }

    @Test
    fun integration() {
        with(markdownResume) {
            addHeader(resume)
            startSection("Section 1")
            makeExperiences(resume)
            startSection("Section 2")
            makeProjectsAndPublications(resume)
            startSection("Section 3")
            makeEducation(resume)
        }

        assertThat(markdownResume.toString()).isEqualTo(
            """
                # First Last
                > Software Engineer • Android Developer
                
                ## Contact Information
                - [email](https://www.email.com)
                - [linkedin](https://www.linkedin.com)
                - [github](https://www.github.com)
                - [location](https://www.gps.com)
                
                ## Section 1
                ### [Company 1](https://www.company.com)
                - Remote
                
                #### SWE 1
                > Oct. 2019 – Dec. 2019
                - worked with kotlin
                
                ## Section 2
                - **[Project X](https://www.projectx.com)**: Project X description
                
                ## Section 3
                - **[Top institution](https://www.topinstitution.edu)**: BSc. in Computer Science (Brazil 2022 – Present)
                
            """.trimIndent()
        )
    }

    private fun MarkdownResume.addHeader(resumeBuilder: ResumeBuilder) {
        val theResume = resumeBuilder.build()
        this.addHeader(
            name = theResume.name,
            headline = theResume.headline,
            contactInformation = theResume.contactInformation,
        )
    }

    private fun MarkdownResume.makeExperiences(resumeBuilder: ResumeBuilder) {
        this.makeExperiences(resumeBuilder.build().jobExperiences)
    }

    private fun MarkdownResume.makeProjectsAndPublications(resume: ResumeBuilder) {
        this.makeProjectsAndPublications(resume.build().projectsAndPublications)
    }

    private fun MarkdownResume.makeEducation(resume: ResumeBuilder) {
        this.makeEducation(resume.build().education)
    }
}

