package alysson.cirilo.resume.drivers

import alysson.cirilo.resume.drivers.factory.ResumeSyntaxFactory
import alysson.cirilo.resume.drivers.test.ResumeSyntaxFactoryTest
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.LinkedInformation
import alysson.cirilo.resume.entities.ProjectOrPublication

class LatexSoberSyntaxFactoryTest : ResumeSyntaxFactoryTest() {

    override fun createSyntaxFactory(): ResumeSyntaxFactory {
        return LatexSoberSyntaxFactory(
            template = "\\begin{document}\n<content>\n\\end{document}",
            contentPlaceholder = "<content>",
        )
    }

    override fun generateEmptyOutput() = wrapAroundDocument("")
    override fun generateSection(sectionName: String) = wrapAroundDocument("\\section{$sectionName}")
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
        secondExperience: JobExperience,
        secondExperienceRole: String,
    ) = wrapAroundDocument(
        """
            \begin{itemize}
                \item \employment
                    {${firstExperience.company.url}}
                    {${firstExperience.company.displayName}}
                    {${firstExperience.location}}
                    {$firstExperienceRole}
                    {Oct. 2019 -- Dec. 2019}
                
                \item \employment
                    {${secondExperience.company.url}}
                    {${secondExperience.company.displayName}}
                    {${secondExperience.location}}
                    {$secondExperienceRole}
                    {Dec. 2019 -- Present}
            \end{itemize}
        """.trimIndent()
    )

    override fun generateSingleRoleExperienceWithNoBullets(
        experience: JobExperience,
        role: String,
    ) = wrapAroundDocument(
        """
            \begin{itemize}
                \item \employment
                    {${experience.company.url}}
                    {${experience.company.displayName}}
                    {${experience.location}}
                    {$role}
                    {Oct. 2019 -- Dec. 2019}
            \end{itemize}
        """.trimIndent()
    )

    override fun generateSingleRoleExperienceWithSkillBullets(
        experience: JobExperience,
        role: String,
        firstBullet: String,
        secondBullet: String,
        thirdBulletFirstPart: String,
        thirdBulletSecondPart: String,
        thirdBulletThirdPart: String,
    ) = wrapAroundDocument(
        """
            \begin{itemize}
                \item \employment
                    {${experience.company.url}}
                    {${experience.company.displayName}}
                    {${experience.location}}
                    {$role}
                    {Oct. 2019 -- Dec. 2019}
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
        firstRoleBullet: String,
        secondRole: String,
        secondRoleBullet: String,
    ) = wrapAroundDocument(
        """
            \begin{itemize}
                \item \employment
                    {${company.url}}
                    {${company.displayName}}
                    {$location}
                    {$firstRole}
                    {Oct. 2019 -- Dec. 2019}
                \begin{itemize}
                    \item $firstRoleBullet
                \end{itemize}
                
                \position
                    {$secondRole}
                    {Dec. 2019 -- Present}
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

    override fun generateSingleDegree(degree: Degree) = wrapAroundDocument(
        """
            \begin{itemize}
                \item \employment
                    {${degree.institution.url}}
                    {${degree.institution.displayName}}
                    {${degree.location}}
                    {${degree.degree}}
                    {2022 -- Present}
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
        bullet: String,
        secondSectionName: String,
        project: ProjectOrPublication,
        thirdSectionName: String,
        degree: Degree,
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
            
            \section{$firstSectionName}
                \begin{itemize}
                    \item \employment
                        {${experience.company.url}}
                        {${experience.company.displayName}}
                        {${experience.location}}
                        {$role}
                        {Oct. 2019 -- Dec. 2019}
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
                        {2022 -- Present}
                \end{itemize}
        """.trimIndent()
    )

    private fun wrapAroundDocument(content: String): String {
        return ("\\begin{document}\n" +
                content.replaceIndent("    ") +
                "\n" +
                "\\end{document}").trimIndent() +
                "\n"
    }
}
