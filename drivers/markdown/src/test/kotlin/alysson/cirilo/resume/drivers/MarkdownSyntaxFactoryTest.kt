package alysson.cirilo.resume.drivers

import alysson.cirilo.resume.drivers.syntaxfactory.ResumeSyntaxFactory
import alysson.cirilo.resume.drivers.test.ResumeSyntaxFactoryTest
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.LinkedInformation
import alysson.cirilo.resume.entities.ProjectOrPublication

class MarkdownSyntaxFactoryTest : ResumeSyntaxFactoryTest() {
    override fun createSyntaxFactory(): ResumeSyntaxFactory {
        return MarkdownSyntaxFactory()
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
            - [${contactInfo.linkedin.displayName}](${contactInfo.linkedin.url})
            - [${contactInfo.github.displayName}](${contactInfo.github.url})
            - [${contactInfo.location.displayName}](${contactInfo.location.url})
            
        """.trimIndent()
    }

    override fun generateTwoExperiencesWithNoBullets(
        firstExperience: JobExperience,
        firstExperienceRole: String,
        secondExperience: JobExperience,
        secondExperienceRole: String,
    ): String {
        return """
            ### [${firstExperience.company.displayName}](${firstExperience.company.url})
            - ${firstExperience.location}
            
            #### $firstExperienceRole
            > Oct. 2019 – Dec. 2019
            
            ### [${secondExperience.company.displayName}](${secondExperience.company.url})
            - ${secondExperience.location}
            
            #### $secondExperienceRole
            > Dec. 2019 – Present
            
        """.trimIndent()
    }

    override fun generateSingleRoleExperienceWithNoBullets(
        experience: JobExperience,
        role: String,
    ): String {
        return """
            ### [${experience.company.displayName}](${experience.company.url})
            - ${experience.location}
            
            #### $role
            > Oct. 2019 – Dec. 2019
            
        """.trimIndent()
    }

    override fun generateSingleRoleExperienceWithSkillBullets(
        experience: JobExperience,
        role: String,
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
            > Oct. 2019 – Dec. 2019
            - $firstBullet
            - **$secondBullet**
            - $thirdBulletFirstPart**$thirdBulletSecondPart**$thirdBulletThirdPart
            
        """.trimIndent()
    }

    override fun generateTwoRoleExperienceWithBullets(
        company: LinkedInformation,
        location: String,
        firstRole: String,
        firstRoleBullet: String,
        secondRole: String,
        secondRoleBullet: String,
    ): String {
        return """
            ### [${company.displayName}](${company.url})
            - $location
            
            #### $firstRole
            > Oct. 2019 – Dec. 2019
            - $firstRoleBullet
            
            #### $secondRole
            > Dec. 2019 – Present
            - $secondRoleBullet
            
        """.trimIndent()
    }

    override fun generateSingleProject(project: ProjectOrPublication): String {
        return """
            - **[${project.title.displayName}](${project.title.url})**: ${project.description}
            
        """.trimIndent()
    }

    override fun generateSingleDegree(degree: Degree): String {
        return """
            - **[${degree.institution.displayName}](${degree.institution.url})**: ${degree.degree} (${degree.location} 2022 – Present)
            
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
        bullet: String,
        secondSectionName: String,
        project: ProjectOrPublication,
        thirdSectionName: String,
        degree: Degree,
    ): String {
        return """
            # $name
            > $firstHeadlineElement • $secondHeadlineElement
            
            ## Contact Information
            - [${contactInfo.email.displayName}](${contactInfo.email.url})
            - [${contactInfo.linkedin.displayName}](${contactInfo.linkedin.url})
            - [${contactInfo.github.displayName}](${contactInfo.github.url})
            - [${contactInfo.location.displayName}](${contactInfo.location.url})
            
            ## $firstSectionName
            ### [${experience.company.displayName}](${experience.company.url})
            - ${experience.location}
            
            #### $role
            > Oct. 2019 – Dec. 2019
            - $bullet
            
            ## $secondSectionName
            - **[${project.title.displayName}](${project.title.url})**: ${project.description}
            
            ## $thirdSectionName
            - **[${degree.institution.displayName}](${degree.institution.url})**: ${degree.degree} (${degree.location} 2022 – Present)
            
        """.trimIndent()
    }
}
