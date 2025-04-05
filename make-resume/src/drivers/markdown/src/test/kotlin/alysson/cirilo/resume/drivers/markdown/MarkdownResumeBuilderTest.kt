package alysson.cirilo.resume.drivers.markdown

import alysson.cirilo.resume.drivers.utils.ResumeBuilder
import alysson.cirilo.resume.drivers.utils.ResumeBuilderTest
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.LinkedInformation
import alysson.cirilo.resume.entities.ProjectOrPublication
import org.junit.jupiter.api.DisplayName
import java.time.format.DateTimeFormatter

@DisplayName("MarkdownResumeBuilderTest")
class MarkdownResumeBuilderTest : ResumeBuilderTest() {
    override fun createResumeBuilder(
        workDateFormatter: DateTimeFormatter,
        educationDateFormatter: DateTimeFormatter,
    ): ResumeBuilder {
        return MarkdownResumeBuilder(workDateFormatter, educationDateFormatter)
    }

    override fun generateEmptyOutput(): String {
        return "\n"
    }

    override fun generateSection(sectionName: String): String {
        return "## $sectionName\n"
    }

    override fun generateHeader(
        name: String,
        firstHeadlineElement: String,
        secondHeadlineElement: String,
        contactInfo: ContactInformation,
    ): String {
        return """
            # $name
            > $firstHeadlineElement • $secondHeadlineElement
            
            ## Contact Information
            - [${contactInfo.email.displayName}](${contactInfo.email.url})
            - [${contactInfo.homepage.displayName}](${contactInfo.homepage.url})
            - [${contactInfo.linkedin.displayName}](${contactInfo.linkedin.url})
            - [${contactInfo.github.displayName}](${contactInfo.github.url})
            - [${contactInfo.location.displayName}](${contactInfo.location.url})
            
        """.trimIndent()
    }

    override fun generateTwoExperiencesWithNoBullets(
        firstExperience: JobExperience,
        firstExperienceRole: String,
        firstExperienceRoleStartDate: String,
        firstExperienceRoleEndDate: String,
        secondExperience: JobExperience,
        secondExperienceRole: String,
        secondExperienceRoleStartDate: String,
    ): String {
        return """
            ### [${firstExperience.company.displayName}](${firstExperience.company.url})
            - ${firstExperience.location}
            
            #### $firstExperienceRole
            > $firstExperienceRoleStartDate – $firstExperienceRoleEndDate
            
            ### [${secondExperience.company.displayName}](${secondExperience.company.url})
            - ${secondExperience.location}
            
            #### $secondExperienceRole
            > $secondExperienceRoleStartDate – Present
            
        """.trimIndent()
    }

    override fun generateSingleRoleExperienceWithNoBullets(
        experience: JobExperience,
        role: String,
        roleStartDate: String,
        roleEndDate: String,
    ): String {
        return """
            ### [${experience.company.displayName}](${experience.company.url})
            - ${experience.location}
            
            #### $role
            > $roleStartDate – $roleEndDate
            
        """.trimIndent()
    }

    override fun generateSingleRoleExperienceWithSkillBullets(
        experience: JobExperience,
        role: String,
        roleStartDate: String,
        roleEndDate: String,
        firstBullet: String,
        secondBullet: String,
        thirdBulletFirstPart: String,
        thirdBulletSecondPart: String,
        thirdBulletThirdPart: String,
    ): String {
        return """
            ### [${experience.company.displayName}](${experience.company.url})
            - ${experience.location}
            
            #### $role
            > $roleStartDate – $roleEndDate
            - $firstBullet
            - **$secondBullet**
            - $thirdBulletFirstPart**$thirdBulletSecondPart**$thirdBulletThirdPart
            
        """.trimIndent()
    }

    override fun generateTwoRoleExperienceWithBullets(
        company: LinkedInformation,
        location: String,
        firstRole: String,
        firstExperienceRoleStartDate: String,
        firstExperienceRoleEndDate: String,
        firstRoleBullet: String,
        secondRole: String,
        secondRoleStartDate: String,
        secondRoleBullet: String,
    ): String {
        return """
            ### [${company.displayName}](${company.url})
            - $location
            
            #### $firstRole
            > $firstExperienceRoleStartDate – $firstExperienceRoleEndDate
            - $firstRoleBullet
            
            #### $secondRole
            > $secondRoleStartDate – Present
            - $secondRoleBullet
            
        """.trimIndent()
    }

    override fun generateSingleProject(project: ProjectOrPublication): String {
        return """
            - **[${project.title.displayName}](${project.title.url})**: ${project.description}
            
        """.trimIndent()
    }

    override fun generateSingleDegree(degree: Degree, startDate: String): String {
        return """
            - **[${degree.institution.displayName}](${degree.institution.url})**: ${degree.degree} (${degree.location} $startDate – Present)
            
        """.trimIndent()
    }

    override fun generateIntegration(
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
    ): String {
        return """
            # $name
            > $firstHeadlineElement • $secondHeadlineElement
            
            ## Contact Information
            - [${contactInfo.email.displayName}](${contactInfo.email.url})
            - [${contactInfo.homepage.displayName}](${contactInfo.homepage.url})
            - [${contactInfo.linkedin.displayName}](${contactInfo.linkedin.url})
            - [${contactInfo.github.displayName}](${contactInfo.github.url})
            - [${contactInfo.location.displayName}](${contactInfo.location.url})
            
            ## $firstSectionName
            ### [${experience.company.displayName}](${experience.company.url})
            - ${experience.location}
            
            #### $role
            > $roleStartDate – $roleEndDate
            - $bullet
            
            ## $secondSectionName
            - **[${project.title.displayName}](${project.title.url})**: ${project.description}
            
            ## $thirdSectionName
            - **[${degree.institution.displayName}](${degree.institution.url})**: ${degree.degree} (${degree.location} $degreeStartDate – Present)
            
        """.trimIndent()
    }
}
