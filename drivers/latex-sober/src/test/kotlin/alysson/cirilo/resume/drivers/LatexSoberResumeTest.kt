package alysson.cirilo.resume.drivers

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
import org.junit.jupiter.api.Test

class LatexSoberResumeTest {

    private val latexSoberResume = LatexSoberResume(
        template = "\\begin{document}\n<content>\n\\end{document}",
        contentPlaceholder = "<content>",
    )

    private fun generateEmptyOutput() = wrapAroundDocument("")
    private fun generateSection(sectionName: String) = wrapAroundDocument("\\section{$sectionName}")
    private fun generateHeader(
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

    private fun generateTwoExperiencesWithNoBullets(
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

    private fun generateSingleRoleExperienceWithNoBullets(
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

    private fun generateSingleRoleExperienceWithSkillBullets(
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

    private fun generateTwoRoleExperienceWithBullets(
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

    private fun generateSingleProject(project: ProjectOrPublication) = wrapAroundDocument(
        """
            \begin{itemize}
                \item \project
                    {${project.title.url}}
                    {${project.title.displayName}}
                    {${project.description}}
            \end{itemize}
        """.trimIndent()
    )

    private fun generateSingleDegree(degree: Degree) = wrapAroundDocument(
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

    private fun generateIntegration(
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

    @Test
    fun `no content generates nothing`() {
        assertEquals(generateEmptyOutput(), latexSoberResume.toString())
    }

    @Test
    fun `can generate a section`() {
        latexSoberResume.startSection("Section Name")

        assertEquals(generateSection("Section Name"), latexSoberResume.toString())
    }

    @Test
    fun `can generate a header`() {
        latexSoberResume.addHeader(Dataset.name, Dataset.headline, Dataset.contactInfo)

        val header = generateHeader(
            Dataset.name,
            Dataset.headline.first(),
            Dataset.headline.last(),
            Dataset.contactInfo,
        )
        assertEquals(header, latexSoberResume.toString())
    }

    @Test
    fun `no job experiences generates nothing`() {
        latexSoberResume.makeExperiences(Dataset.noExperience)

        assertEquals(generateEmptyOutput(), latexSoberResume.toString())
    }

    @Test
    fun `can generate job experiences`() {
        latexSoberResume.makeExperiences(Dataset.twoExperiencesWithNoBullets)

        val output = generateTwoExperiencesWithNoBullets(
            firstExperience = Dataset.twoExperiencesWithNoBullets.first(),
            firstExperienceRole = Dataset.twoExperiencesWithNoBullets.first().roles.first().title,
            secondExperience = Dataset.twoExperiencesWithNoBullets.last(),
            secondExperienceRole = Dataset.twoExperiencesWithNoBullets.last().roles.first().title,
        )
        assertEquals(output, latexSoberResume.toString())
    }

    @Test
    fun `can generate single role`() {
        latexSoberResume.makeExperiences(Dataset.singleRoleExperienceWithNoBullets)

        val output = generateSingleRoleExperienceWithNoBullets(
            experience = Dataset.singleRoleExperienceWithNoBullets.first(),
            role = Dataset.singleRoleExperienceWithNoBullets.first().roles.first().title,
        )
        assertEquals(output, latexSoberResume.toString())
    }

    @Test
    fun `can generate role with bullets`() {
        latexSoberResume.makeExperiences(Dataset.singleRoleExperienceWithSkillBullets)

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
        assertEquals(output, latexSoberResume.toString())
    }


    @Test
    fun `can generate multiple roles`() {
        latexSoberResume.makeExperiences(Dataset.twoRoleExperienceWithBullets)

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
        assertEquals(output, latexSoberResume.toString())
    }

    @Test
    fun `no projects and publications generates nothing`() {
        latexSoberResume.makeProjectsAndPublications(Dataset.noProjectsOrPublications)

        assertEquals(generateEmptyOutput(), latexSoberResume.toString())
    }

    @Test
    fun `can generate project and publications`() {
        latexSoberResume.makeProjectsAndPublications(Dataset.singleProject)

        val output = generateSingleProject(Dataset.singleProject.first())
        assertEquals(output, latexSoberResume.toString())
    }

    @Test
    fun `no education generates nothing`() {
        latexSoberResume.makeEducation(Dataset.noEducation)

        assertEquals(generateEmptyOutput(), latexSoberResume.toString())
    }

    @Test
    fun `can generate education`() {
        latexSoberResume.makeEducation(Dataset.singleDegree)

        assertEquals(
            generateSingleDegree(Dataset.singleDegree.first()),
            latexSoberResume.toString()
        )
    }

    @Test
    fun integration() {
        with(latexSoberResume) {
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
        assertEquals(output, latexSoberResume.toString())
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
