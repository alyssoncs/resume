package alysson.cirilo.resume.drivers

import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.JobExperience
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

    @Test
    fun `no content generates nothing`() {
        assertGenerates("")
    }

    @Test
    fun `can generate a section`() {
        latexSoberResume.startSection("Section Name")

        assertGenerates("\\section{Section Name}")
    }

    @Test
    fun `can generate a header`() {
        latexSoberResume.addHeader(Dataset.name, Dataset.headline, Dataset.contactInfo)

        assertGenerates(
            """
                % constants
                \newcommand{\name}{First Last}
                \newcommand{\mytitle}{{\huge\textbf{\name}}}
                \newcommand{\headline}{Software Engineer{\enskip\starredbullet\enskip}Android Developer}
                \newcommand{\email}
                    {\iconhref{https://www.email.com}{{\scriptsize\faEnvelope{}} email}}
                \newcommand{\linkedin}
                    {\iconhref{https://www.linkedin.com}{\faLinkedin{} linkedin}}
                \newcommand{\github}
                    {\iconhref{https://www.github.com}{\faGithub{} github}}
                \newcommand{\address}
                    {\hspace{1pt}\iconhref{https://www.gps.com}{\faMapMarker{}\hspace{1pt} location}}
                
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
    }

    @Test
    fun `no job experiences generates nothing`() {
        latexSoberResume.makeExperiences(Dataset.noExperience)

        assertGenerates("")
    }

    @Test
    fun `can generate job experiences`() {
        latexSoberResume.makeExperiences(Dataset.twoExperiencesWithNoBullets)

        assertGenerates(
            """
                \begin{itemize}
                    \item \employment
                        {https://www.company.com}
                        {Company 1}
                        {Remote}
                        {SWE 1}
                        {Oct. 2019 -- Dec. 2019}
                    
                    \item \employment
                        {https://www.company.com}
                        {Company 2}
                        {Remote}
                        {SWE 2}
                        {Dec. 2019 -- Present}
                \end{itemize}
            """.trimIndent()
        )
    }

    @Test
    fun `can generate single role`() {
        latexSoberResume.makeExperiences(Dataset.singleRoleExperienceWithNoBullets)

        assertGenerates(
            """
                \begin{itemize}
                    \item \employment
                        {https://www.company.com}
                        {Company 1}
                        {Remote}
                        {SWE 1}
                        {Oct. 2019 -- Dec. 2019}
                \end{itemize}
            """.trimIndent()
        )
    }

    @Test
    fun `can generate role with bullets`() {
        latexSoberResume.makeExperiences(Dataset.singleRoleExperienceWithSkillBullets)

        assertGenerates(
            """
                \begin{itemize}
                    \item \employment
                        {https://www.company.com}
                        {Company 1}
                        {Remote}
                        {SWE 1}
                        {Oct. 2019 -- Dec. 2019}
                    \begin{itemize}
                        \item delivered value
                        \item \textbf{android}
                        \item delivered value with \textbf{kotlin} but at what cost?
                    \end{itemize}
                \end{itemize}
            """.trimIndent()
        )
    }

    @Test
    fun `can generate multiple roles`() {
        latexSoberResume.makeExperiences(Dataset.twoRoleExperienceWithBullets)

        assertGenerates(
            """
                \begin{itemize}
                    \item \employment
                        {https://www.company.com}
                        {Company 1}
                        {Remote}
                        {SWE 1}
                        {Oct. 2019 -- Dec. 2019}
                    \begin{itemize}
                        \item worked with kotlin
                    \end{itemize}
                    
                    \position
                        {SWE 2}
                        {Dec. 2019 -- Present}
                    \begin{itemize}
                        \item been promoted
                    \end{itemize}
                \end{itemize}
            """.trimIndent()
        )
    }

    @Test
    fun `no projects and publications generates nothing`() {
        latexSoberResume.makeProjectsAndPublications(Dataset.noProjectsOrPublications)

        assertGenerates("")
    }

    @Test
    fun `can generate project and publications`() {
        latexSoberResume.makeProjectsAndPublications(Dataset.singleProject)

        assertGenerates(
            """
                \begin{itemize}
                    \item \project
                        {https://www.projectx.com}
                        {Project X}
                        {Project X description}
                \end{itemize}
            """.trimIndent()
        )
    }

    @Test
    fun `no education generates nothing`() {
        latexSoberResume.makeEducation(Dataset.noEducation)

        assertGenerates("")
    }

    @Test
    fun `can generate education`() {
        latexSoberResume.makeEducation(Dataset.singleDegree)

        assertGenerates(
            """
                \begin{itemize}
                    \item \employment
                        {https://www.topinstitution.edu}
                        {Top institution}
                        {Brazil}
                        {BSc. in Computer Science}
                        {2022 -- Present}
                \end{itemize}
            """.trimIndent()
        )
    }

    @Test
    fun integration() {
        with(latexSoberResume) {
            addHeader(Dataset.name, Dataset.headline, Dataset.contactInfo)
            startSection("Section 1")
            makeExperiences(Dataset.singleRoleExperienceWithTextBullets)
            startSection("Section 2")
            makeProjectsAndPublications(Dataset.singleProject)
            startSection("Section 3")
            makeEducation(Dataset.singleDegree)
        }

        assertGenerates(
            """
                % constants
                \newcommand{\name}{First Last}
                \newcommand{\mytitle}{{\huge\textbf{\name}}}
                \newcommand{\headline}{Software Engineer{\enskip\starredbullet\enskip}Android Developer}
                \newcommand{\email}
                    {\iconhref{https://www.email.com}{{\scriptsize\faEnvelope{}} email}}
                \newcommand{\linkedin}
                    {\iconhref{https://www.linkedin.com}{\faLinkedin{} linkedin}}
                \newcommand{\github}
                    {\iconhref{https://www.github.com}{\faGithub{} github}}
                \newcommand{\address}
                    {\hspace{1pt}\iconhref{https://www.gps.com}{\faMapMarker{}\hspace{1pt} location}}
                
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
                
                \section{Section 1}
                    \begin{itemize}
                        \item \employment
                            {https://www.company.com}
                            {Company 1}
                            {Remote}
                            {SWE 1}
                            {Oct. 2019 -- Dec. 2019}
                        \begin{itemize}
                            \item worked with kotlin
                        \end{itemize}
                    \end{itemize}
                
                \section{Section 2}
                    \begin{itemize}
                        \item \project
                            {https://www.projectx.com}
                            {Project X}
                            {Project X description}
                    \end{itemize}
                
                \section{Section 3}
                    \begin{itemize}
                        \item \employment
                            {https://www.topinstitution.edu}
                            {Top institution}
                            {Brazil}
                            {BSc. in Computer Science}
                            {2022 -- Present}
                    \end{itemize}
            """.trimIndent()
        )
    }

    private fun assertGenerates(expectedOutput: String) {
        assertEquals(
            wrapAroundDocument(expectedOutput),
            latexSoberResume.toString(),
        )
    }

    private fun wrapAroundDocument(expectedOutput: String): String {
        return ("\\begin{document}\n" +
                expectedOutput.replaceIndent("    ") +
                "\n" +
                "\\end{document}").trimIndent() +
                "\n"
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

            val singleRoleExperienceWithTextBullets = listOf(Builders.jobExperienceBuilder.build())

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
