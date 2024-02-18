package alysson.cirilo.resume.drivers.latexawesome

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

class LatexAwesomeSyntaxFactoryTest : ResumeSyntaxFactoryTest() {
    private val template = """
        <header>
        \begin{document}
        <content>
        \end{document}
    """.trimIndent()

    override fun createSyntaxFactory(
        workDateFormatter: DateTimeFormatter,
        educationDateFormatter: DateTimeFormatter,
    ): ResumeSyntaxFactory {
        return LatexAwesomeSyntaxFactory(
            template = template,
            headerPlaceholder = "<header>",
            contentPlaceholder = "<content>",
            workDateFormatter = workDateFormatter,
            educationDateFormatter = educationDateFormatter,
        )
    }

    override fun generateEmptyOutput(): String = wrapAroundDocument(header = "", content = "")

    override fun generateSection(sectionName: String): String = wrapAroundDocument(
        header = "",
        content = """
            \cvsection{$sectionName}
        """.trimIndent(),
    )

    override fun generateHeader(
        name: String,
        firstHeadlineElement: String,
        secondHeadlineElement: String,
        contactInfo: ContactInformation,
    ): String = wrapAroundDocument(
        header = """
            \name{${name.substringBefore(' ')}}{${name.substringAfter(' ')}}
            \position{$firstHeadlineElement{\enskip\starredbullet\enskip}$secondHeadlineElement}
            \address{${contactInfo.location.displayName}}
            \email{${contactInfo.email.displayName}}
            \github{${contactInfo.github.displayName}}
            \linkedin{${contactInfo.linkedin.displayName}}
        """.trimIndent(),
        content = "\\makecvheader[C]\n\n",
    )

    override fun generateTwoExperiencesWithNoBullets(
        firstExperience: JobExperience,
        firstExperienceRole: String,
        firstExperienceRoleStartDate: String,
        firstExperienceRoleEndDate: String,
        secondExperience: JobExperience,
        secondExperienceRole: String,
        secondExperienceRoleStartDate: String,
    ): String {
        return wrapAroundDocument(
            header = "",
            content = """
                \begin{cventries}
                    \cventry
                        {$firstExperienceRole}
                        {\iconhref{${firstExperience.company.url}}{${firstExperience.company.displayName}}}
                        {${firstExperience.location}}
                        {$firstExperienceRoleStartDate -- $firstExperienceRoleEndDate}
                        {}
                    
                    \cventry
                        {$secondExperienceRole}
                        {\iconhref{${secondExperience.company.url}}{${secondExperience.company.displayName}}}
                        {${secondExperience.location}}
                        {$secondExperienceRoleStartDate -- Present}
                        {}
                \end{cventries}
            """.trimIndent(),
        )
    }

    override fun generateSingleRoleExperienceWithNoBullets(
        experience: JobExperience,
        role: String,
        roleStartDate: String,
        roleEndDate: String,
    ): String = wrapAroundDocument(
        header = "",
        content = """
            \begin{cventries}
                \cventry
                    {$role}
                    {\iconhref{${experience.company.url}}{${experience.company.displayName}}}
                    {${experience.location}}
                    {$roleStartDate -- $roleEndDate}
                    {}
            \end{cventries}
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
        header = "",
        content = """
            \begin{cventries}
                \cventry
                    {$role}
                    {\iconhref{${experience.company.url}}{${experience.company.displayName}}}
                    {${experience.location}}
                    {$roleStartDate -- $roleEndDate}
                    {
                        \begin{cvitems}
                            \item $firstBullet
                            \item \textbf{$secondBullet}
                            \item $thirdBulletFirstPart\textbf{$thirdBulletSecondPart}$thirdBulletThirdPart
                        \end{cvitems}
                    }
            \end{cventries}
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
        header = "",
        content = """
            \begin{cventries}
                \cventry
                    {$firstRole}
                    {\iconhref{${company.url}}{${company.displayName}}}
                    {$location}
                    {$firstExperienceRoleStartDate -- $firstExperienceRoleEndDate}
                    {
                        \begin{cvitems}
                            \item $firstRoleBullet
                        \end{cvitems}
                    }
                
                \cventry
                    {$secondRole}
                    {}
                    {}
                    {$secondRoleStartDate -- Present}
                    {
                        \begin{cvitems}
                            \item $secondRoleBullet
                        \end{cvitems}
                    }
            \end{cventries}
        """.trimIndent(),
    )

    override fun generateSingleProject(project: ProjectOrPublication): String = wrapAroundDocument(
        header = "",
        content = """
            \vspace{-15pt}
            \begin{cventries}
                \cventry
                    {}
                    {}
                    {}
                    {}
                    {
                        \begin{cvitems}
                            \item \textbf{\iconhref{${project.title.url}}{${project.title.displayName}}:} ${project.description}
                        \end{cvitems}
                    }
            \end{cventries}
        """.trimIndent(),
    )

    override fun generateSingleDegree(degree: Degree, startDate: String): String = wrapAroundDocument(
        header = "",
        content = """
            \begin{cventries}
                \cventry
                    {${degree.degree}}
                    {\iconhref{${degree.institution.url}}{${degree.institution.displayName}}}
                    {${degree.location}}
                    {$startDate -- Present}
                    {}
            \end{cventries}
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
        header = """
            \name{${name.substringBefore(' ')}}{${name.substringAfter(' ')}}
            \position{$firstHeadlineElement{\enskip\starredbullet\enskip}$secondHeadlineElement}
            \address{${contactInfo.location.displayName}}
            \email{${contactInfo.email.displayName}}
            \github{${contactInfo.github.displayName}}
            \linkedin{${contactInfo.linkedin.displayName}}
        """.trimIndent(),
        content = """
            \makecvheader[C]
            
            \cvsection{$firstSectionName}
                \begin{cventries}
                    \cventry
                        {$role}
                        {\iconhref{${experience.company.url}}{${experience.company.displayName}}}
                        {${experience.location}}
                        {$roleStartDate -- $roleEndDate}
                        {
                            \begin{cvitems}
                                \item $bullet
                            \end{cvitems}
                        }
                \end{cventries}
            
            \cvsection{$secondSectionName}
                \vspace{-15pt}
                \begin{cventries}
                    \cventry
                        {}
                        {}
                        {}
                        {}
                        {
                            \begin{cvitems}
                                \item \textbf{\iconhref{${project.title.url}}{${project.title.displayName}}:} ${project.description}
                            \end{cvitems}
                        }
                \end{cventries}
            
            \cvsection{$thirdSectionName}
                \begin{cventries}
                    \cventry
                        {${degree.degree}}
                        {\iconhref{${degree.institution.url}}{${degree.institution.displayName}}}
                        {${degree.location}}
                        {$degreeStartDate -- Present}
                        {}
                \end{cventries}
        """.trimIndent(),
    )

    @Test
    fun `ampersand in the section should be escaped`() {
        syntaxFactory.startSection("this & that")

        assertEquals(
            wrapAroundDocument("", "\\cvsection{this \\& that}"),
            syntaxFactory.create(),
        )
    }

    private fun wrapAroundDocument(header: String, content: String): String {
        return header +
            "\n" +
            "\\begin{document}\n" +
            content.replaceIndent("    ") +
            "\n" +
            "\\end{document}" +
            "\n"
    }
}
