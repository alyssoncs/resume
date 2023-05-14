package alysson.cirilo.resume.drivers.latexawesome

import alysson.cirilo.resume.drivers.utils.syntaxfactory.ResumeSyntaxFactory
import alysson.cirilo.resume.drivers.test.ResumeSyntaxFactoryTest
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.LinkedInformation
import alysson.cirilo.resume.entities.ProjectOrPublication

class LatexAwesomeSyntaxFactoryTest : ResumeSyntaxFactoryTest() {
    private val template = """
        <header>
        \begin{document}
        <content>
        \end{document}
    """.trimIndent()

    override fun createSyntaxFactory(): ResumeSyntaxFactory {
        return LatexAwesomeSyntaxFactory(
            template = template,
            headerPlaceholder = "<header>",
            contentPlaceholder = "<content>"
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
        secondExperience: JobExperience,
        secondExperienceRole: String,
    ): String {
        return wrapAroundDocument(
            header = "",
            content = """
                \begin{cventries}
                    \cventry
                        {$firstExperienceRole}
                        {${firstExperience.company.displayName}}
                        {${firstExperience.location}}
                        {Oct. 2019 -- Dec. 2019}
                        {}
                    
                    \cventry
                        {$secondExperienceRole}
                        {${secondExperience.company.displayName}}
                        {${secondExperience.location}}
                        {Dec. 2019 -- Present}
                        {}
                \end{cventries}
            """.trimIndent(),
        )
    }

    override fun generateSingleRoleExperienceWithNoBullets(
        experience: JobExperience,
        role: String,
    ): String = wrapAroundDocument(
        header = "",
        content = """
            \begin{cventries}
                \cventry
                    {$role}
                    {${experience.company.displayName}}
                    {${experience.location}}
                    {Oct. 2019 -- Dec. 2019}
                    {}
            \end{cventries}
        """.trimIndent(),
    )

    override fun generateSingleRoleExperienceWithSkillBullets(
        experience: JobExperience,
        role: String,
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
                    {${experience.company.displayName}}
                    {${experience.location}}
                    {Oct. 2019 -- Dec. 2019}
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
        firstRoleBullet: String,
        secondRole: String,
        secondRoleBullet: String,
    ): String = wrapAroundDocument(
        header = "",
        content = """
            \begin{cventries}
                \cventry
                    {$firstRole}
                    {${company.displayName}}
                    {${location}}
                    {Oct. 2019 -- Dec. 2019}
                    {
                        \begin{cvitems}
                            \item $firstRoleBullet
                        \end{cvitems}
                    }
                
                \cventry
                    {$secondRole}
                    {}
                    {}
                    {Dec. 2019 -- Present}
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
                            \item \textbf{${project.title.displayName}:} ${project.description}
                        \end{cvitems}
                    }
            \end{cventries}
        """.trimIndent(),
    )

    override fun generateSingleDegree(degree: Degree): String = wrapAroundDocument(
        header = "",
        content = """
            \begin{cventries}
                \cventry
                    {${degree.degree}}
                    {${degree.institution.displayName}}
                    {${degree.location}}
                    {2022 -- Present}
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
        bullet: String,
        secondSectionName: String,
        project: ProjectOrPublication,
        thirdSectionName: String,
        degree: Degree,
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
                        {${experience.company.displayName}}
                        {${experience.location}}
                        {Oct. 2019 -- Dec. 2019}
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
                                \item \textbf{${project.title.displayName}:} ${project.description}
                            \end{cvitems}
                        }
                \end{cventries}
            
            \cvsection{$thirdSectionName}
                \begin{cventries}
                    \cventry
                        {${degree.degree}}
                        {${degree.institution.displayName}}
                        {${degree.location}}
                        {2022 -- Present}
                        {}
                \end{cventries}
        """.trimIndent()
    )

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
