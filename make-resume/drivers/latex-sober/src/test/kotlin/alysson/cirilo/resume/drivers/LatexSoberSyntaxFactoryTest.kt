package alysson.cirilo.resume.drivers

import alysson.cirilo.resume.drivers.utils.syntaxfactory.ResumeSyntaxFactory
import alysson.cirilo.resume.drivers.test.ResumeSyntaxFactoryTest
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.LinkedInformation
import alysson.cirilo.resume.entities.ProjectOrPublication
import alysson.cirilo.resume.drivers.latexsober.LatexSoberSyntaxFactory
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
            \newcommand{\name}{$name}
            \newcommand{\mytitle}{{\huge\textbf{\name}}}
            \newcommand{\headline}{$firstHeadlineElement{\enskip\starredbullet\enskip}$secondHeadlineElement}
            \newcommand{\email}
                {\iconhref{${contactInfo.email.url}}{{\scriptsize\faEnvelope{}} ${contactInfo.email.displayName}}}
            \newcommand{\linkedin}
                {\iconhref{${contactInfo.linkedin.url}}{\faLinkedin{} ${contactInfo.linkedin.displayName}}}
            \newcommand{\github}
                {\iconhref{${contactInfo.github.url}}{\faGithub{} ${contactInfo.github.displayName}}}
            \newcommand{\address}
                {\hspace{1pt}\iconhref{${contactInfo.location.url}}{\faMapMarker{}\hspace{1pt} ${contactInfo.location.displayName}}}
            
            \begin{minipage}[t]{0.70\linewidth}%743
                \mytitle\\
                \headline
            \end{minipage}
            \hspace{8pt}
            \begin{minipage}[t]{0.277\linewidth}
                {\flushleft\small
                    \email\\
                    \linkedin\\
                    \github\\
                    \address
                }
            \end{minipage}
        """.trimIndent()
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
        """.trimIndent()
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
        """.trimIndent()
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
        """.trimIndent()
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
        """.trimIndent()
    )

    override fun generateSingleProject(project: ProjectOrPublication) = wrapAroundDocument(
        """
            \begin{itemize}
                \item \project
                    {${project.title.url}}
                    {${project.title.displayName}}
                    {${project.description}}
            \end{itemize}
        """.trimIndent()
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
        """.trimIndent()
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
        degreeStartDate: String
    ): String = wrapAroundDocument(
        """
            % constants
            \newcommand{\name}{$name}
            \newcommand{\mytitle}{{\huge\textbf{\name}}}
            \newcommand{\headline}{$firstHeadlineElement{\enskip\starredbullet\enskip}$secondHeadlineElement}
            \newcommand{\email}
                {\iconhref{${contactInfo.email.url}}{{\scriptsize\faEnvelope{}} ${contactInfo.email.displayName}}}
            \newcommand{\linkedin}
                {\iconhref{${contactInfo.linkedin.url}}{\faLinkedin{} ${contactInfo.linkedin.displayName}}}
            \newcommand{\github}
                {\iconhref{${contactInfo.github.url}}{\faGithub{} ${contactInfo.github.displayName}}}
            \newcommand{\address}
                {\hspace{1pt}\iconhref{${contactInfo.location.url}}{\faMapMarker{}\hspace{1pt} ${contactInfo.location.displayName}}}
            
            \begin{minipage}[t]{0.70\linewidth}%743
                \mytitle\\
                \headline
            \end{minipage}
            \hspace{8pt}
            \begin{minipage}[t]{0.277\linewidth}
                {\flushleft\small
                    \email\\
                    \linkedin\\
                    \github\\
                    \address
                }
            \end{minipage}
            
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
        """.trimIndent()
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
