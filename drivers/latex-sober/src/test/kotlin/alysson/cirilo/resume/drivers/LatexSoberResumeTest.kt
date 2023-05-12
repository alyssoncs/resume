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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class LatexSoberResumeTest {

    private val latexSoberResume = LatexSoberResume(
        template = "\\begin{document}\n<content>\n\\end{document}",
        contentPlaceholder = "<content>",
    )

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
        assertGenerates("")
    }

    @Test
    fun `can generate a section`() {
        latexSoberResume.startSection("Section Name")

        assertGenerates("\\section{Section Name}")
    }

    @Test
    fun `can generate a header`() {
        latexSoberResume.addHeader(resume)

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
        latexSoberResume.makeExperiences(resume.withNoExperience())

        assertGenerates("")
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

        latexSoberResume.makeExperiences(resume)

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
        val resume = resume
            .with(jobExperience.with(role.withNoBulletPoints()))

        latexSoberResume.makeExperiences(resume)

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

        latexSoberResume.makeExperiences(resume)

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

        latexSoberResume.makeExperiences(resume)

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
        latexSoberResume.makeProjectsAndPublications(resume.withNoProjectsOrPublications())

        assertGenerates("")
    }

    @Test
    fun `can generate project and publications`() {
        latexSoberResume.makeProjectsAndPublications(resume)

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
        latexSoberResume.makeEducation(resume.withNoEducation())

        assertGenerates("")
    }


    @Test
    fun `can generate education`() {
        latexSoberResume.makeEducation(resume)

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
            addHeader(resume)
            startSection("Section 1")
            makeExperiences(resume)
            startSection("Section 2")
            makeProjectsAndPublications(resume)
            startSection("Section 3")
            makeEducation(resume)
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

    private fun LatexSoberResume.addHeader(resumeBuilder: ResumeBuilder) {
        val theResume = resumeBuilder.build()
        this.addHeader(
            name = theResume.name,
            headline = theResume.headline,
            contactInformation = theResume.contactInformation,
        )
    }

    private fun LatexSoberResume.makeExperiences(resumeBuilder: ResumeBuilder) {
        this.makeExperiences(resumeBuilder.build().jobExperiences)
    }

    private fun LatexSoberResume.makeProjectsAndPublications(resume: ResumeBuilder) {
        this.makeProjectsAndPublications(resume.build().projectsAndPublications)
    }

    private fun LatexSoberResume.makeEducation(resume: ResumeBuilder) {
        this.makeEducation(resume.build().education)
    }
}
