package alysson.cirilo.resume.drivers.latexsober

import alysson.cirilo.resume.drivers.utils.syntaxfactory.ResumeSyntaxFactory
import alysson.cirilo.resume.drivers.utils.syntaxfactory.ResumeSyntaxFactoryTest
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.LinkedInformation
import alysson.cirilo.resume.entities.ProjectOrPublication
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.format.DateTimeFormatter

class LatexSoberSyntaxFactoryTest : ResumeSyntaxFactoryTest() {

    override fun createSyntaxFactory(
        workDateFormatter: DateTimeFormatter,
        educationDateFormatter: DateTimeFormatter,
    ): ResumeSyntaxFactory {
        return LatexSoberSyntaxFactory(
            template = "\\begin{document}\n<content>\n\\end{document}",
            contentPlaceholder = "<content>",
            workDateFormatter = workDateFormatter,
            educationDateFormatter = educationDateFormatter,
        )
    }

    override fun generateEmptyOutput() = wrapAroundDocument("")
    override fun generateSection(sectionName: String) =
        wrapAroundDocument("\\section{$sectionName}")

    override fun generateHeader(
        name: String,
        firstHeadlineElement: String,
        secondHeadlineElement: String,
        contactInfo: ContactInformation,
    ) = wrapAroundDocument(
        """
            % constants
            \name{$name}
            \headline{$firstHeadlineElement{\enskip\starredbullet\enskip}$secondHeadlineElement}
            \email{${contactInfo.email.url}}{${contactInfo.email.displayName}}
            \linkedin{${contactInfo.linkedin.url}}{${contactInfo.linkedin.displayName}}
            \github{${contactInfo.github.url}}{${contactInfo.github.displayName}}
            \address{${contactInfo.location.url}}{${contactInfo.location.displayName}}
            
            \makeheader
        """.trimIndent(),
    )

    override fun generateTwoExperiencesWithNoBullets(
        firstExperience: JobExperience,
        firstExperienceRole: String,
        firstExperienceRoleStartDate: String,
        firstExperienceRoleEndDate: String,
        secondExperience: JobExperience,
        secondExperienceRole: String,
        secondExperienceRoleStartDate: String,
    ): String = wrapAroundDocument(
        """
            \begin{itemize}
                \item \employment
                    {${firstExperience.company.url}}
                    {${firstExperience.company.displayName}}
                    {${firstExperience.location}}
                    {$firstExperienceRole}
                    {$firstExperienceRoleStartDate -- $firstExperienceRoleEndDate}
                
                \item \employment
                    {${secondExperience.company.url}}
                    {${secondExperience.company.displayName}}
                    {${secondExperience.location}}
                    {$secondExperienceRole}
                    {$secondExperienceRoleStartDate -- Present}
            \end{itemize}
        """.trimIndent(),
    )

    override fun generateSingleRoleExperienceWithNoBullets(
        experience: JobExperience,
        role: String,
        roleStartDate: String,
        roleEndDate: String,
    ): String = wrapAroundDocument(
        """
            \begin{itemize}
                \item \employment
                    {${experience.company.url}}
                    {${experience.company.displayName}}
                    {${experience.location}}
                    {$role}
                    {$roleStartDate -- $roleEndDate}
            \end{itemize}
        """.trimIndent(),
    )

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
    ): String = wrapAroundDocument(
        """
            \begin{itemize}
                \item \employment
                    {${experience.company.url}}
                    {${experience.company.displayName}}
                    {${experience.location}}
                    {$role}
                    {$roleStartDate -- $roleEndDate}
                \begin{itemize}
                    \item $firstBullet
                    \item \textbf{$secondBullet}
                    \item $thirdBulletFirstPart\textbf{$thirdBulletSecondPart}$thirdBulletThirdPart
                \end{itemize}
            \end{itemize}
        """.trimIndent(),
    )

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
    ): String = wrapAroundDocument(
        """
            \begin{itemize}
                \item \employment
                    {${company.url}}
                    {${company.displayName}}
                    {$location}
                    {$firstRole}
                    {$firstExperienceRoleStartDate -- $firstExperienceRoleEndDate}
                \begin{itemize}
                    \item $firstRoleBullet
                \end{itemize}
                
                \position
                    {$secondRole}
                    {$secondRoleStartDate -- Present}
                \begin{itemize}
                    \item $secondRoleBullet
                \end{itemize}
            \end{itemize}
        """.trimIndent(),
    )

    override fun generateSingleProject(project: ProjectOrPublication) = wrapAroundDocument(
        """
            \begin{itemize}
                \item \project
                    {${project.title.url}}
                    {${project.title.displayName}}
                    {${project.description}}
            \end{itemize}
        """.trimIndent(),
    )

    override fun generateSingleDegree(degree: Degree, startDate: String): String = wrapAroundDocument(
        """
            \begin{itemize}
                \item \employment
                    {${degree.institution.url}}
                    {${degree.institution.displayName}}
                    {${degree.location}}
                    {${degree.degree}}
                    {$startDate -- Present}
            \end{itemize}
        """.trimIndent(),
    )

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
    ): String = wrapAroundDocument(
        """
            % constants
            \name{$name}
            \headline{$firstHeadlineElement{\enskip\starredbullet\enskip}$secondHeadlineElement}
            \email{${contactInfo.email.url}}{${contactInfo.email.displayName}}
            \linkedin{${contactInfo.linkedin.url}}{${contactInfo.linkedin.displayName}}
            \github{${contactInfo.github.url}}{${contactInfo.github.displayName}}
            \address{${contactInfo.location.url}}{${contactInfo.location.displayName}}
            
            \makeheader
            
            \section{$firstSectionName}
                \begin{itemize}
                    \item \employment
                        {${experience.company.url}}
                        {${experience.company.displayName}}
                        {${experience.location}}
                        {$role}
                        {$roleStartDate -- $roleEndDate}
                    \begin{itemize}
                        \item $bullet
                    \end{itemize}
                \end{itemize}
            
            \section{$secondSectionName}
                \begin{itemize}
                    \item \project
                        {${project.title.url}}
                        {${project.title.displayName}}
                        {${project.description}}
                \end{itemize}
            
            \section{$thirdSectionName}
                \begin{itemize}
                    \item \employment
                        {${degree.institution.url}}
                        {${degree.institution.displayName}}
                        {${degree.location}}
                        {${degree.degree}}
                        {$degreeStartDate -- Present}
                \end{itemize}
        """.trimIndent(),
    )

    @Test
    fun `ampersand in the section should be italic`() {
        syntaxFactory.startSection("this & that")

        assertEquals(
            wrapAroundDocument("\\section{this \\textit{\\&} that}"),
            syntaxFactory.create(),
        )
    }

    private fun wrapAroundDocument(content: String): String {
        return "\\begin{document}\n" +
            content.replaceIndent("    ") +
            "\n" +
            "\\end{document}" +
            "\n"
    }
}
