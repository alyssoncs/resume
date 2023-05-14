package alysson.cirilo.resume.drivers

import alysson.cirilo.resume.drivers.factory.ResumeSyntaxFactory
import alysson.cirilo.resume.entities.BulletPoint
import alysson.cirilo.resume.entities.BulletPointContent
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.EnrollmentPeriod
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.ProjectOrPublication
import alysson.cirilo.resume.entities.Role
import java.time.format.DateTimeFormatter
import java.util.Locale

class LatexSoberSyntaxFactory(
    private val template: String,
    private val contentPlaceholder: String,
) : ResumeSyntaxFactory {

    private var output = ""
    private var currentIndent = 0
    private var sectionIndent: Int? = null

    private fun updateOutput(newContent: String) {
        val separator = if (output.isEmpty()) "" else "\n"
        output += separator + newContent
    }

    override fun addHeader(name: String, headline: List<String>, contactInformation: ContactInformation) {
        updateOutput(
            """
            % constants
            \newcommand{\name}{${name}}
            \newcommand{\mytitle}{{\huge\textbf{\name}}}
            \newcommand{\headline}{${headline.joinToString(separator = "{\\enskip\\starredbullet\\enskip}") { it }}}
            \newcommand{\email}
                {\iconhref{${contactInformation.email.url}}{{\scriptsize\faEnvelope{}} ${contactInformation.email.displayName}}}
            \newcommand{\linkedin}
                {\iconhref{${contactInformation.linkedin.url}}{\faLinkedin{} ${contactInformation.linkedin.displayName}}}
            \newcommand{\github}
                {\iconhref{${contactInformation.github.url}}{\faGithub{} ${contactInformation.github.displayName}}}
            \newcommand{\address}
                {\hspace{1pt}\iconhref{${contactInformation.location.url}}{\faMapMarker{}\hspace{1pt} ${contactInformation.location.displayName}}}
            
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
        """.reindent(currentIndent)
        )
    }

    override fun startSection(name: String) {
        if (sectionIndent == null) {
            sectionIndent = currentIndent
        }
        sectionIndent?.let { theSectionIndent ->
            updateOutput(
                "\n" +
                        """
                        \section{${name.replace("&", "\\textit{\\&}")}}
                        """.reindent(theSectionIndent)
            )
            currentIndent = theSectionIndent.inc()
        }
    }

    override fun makeExperiences(jobExperiences: List<JobExperience>) {
        makeJobExperiences(jobExperiences)?.let {
            updateOutput(it.reindent(currentIndent))
        }
    }

    override fun makeProjectsAndPublications(projectsAndPublications: List<ProjectOrPublication>) {
        itemize(projectsAndPublications.map(::makeProjectOrPublication))?.let { itemizedProjectAnPubs ->
            updateOutput(itemizedProjectAnPubs.reindent(currentIndent))
        }
    }

    override fun makeEducation(education: List<Degree>) {
        itemize(education.map(::makeDegree))?.let { itemizedDegrees ->
            updateOutput(itemizedDegrees.reindent(currentIndent))
        }
    }

    override fun create(): String {
        return template.replace(contentPlaceholder, output.reindent(1)) + "\n"
    }

    private fun makeJobExperiences(jobExperiences: List<JobExperience>): String? {
        return itemize(jobExperiences.map(::makeJobExperience), "\n\n")
    }

    private fun makeJobExperience(jobExperience: JobExperience): String {
        return listOfNotNull(
            makeFirstRole(jobExperience),
            makeOtherRoles(jobExperience),
        ).joinToString("\n\n")
    }

    private fun makeFirstRole(jobExperience: JobExperience): String {
        return listOfNotNull(
            makeEmployment(jobExperience),
            makeBulletPoints(jobExperience.roles.first().bulletPoints),
        ).joinToString("\n")
    }

    private fun makeEmployment(jobExperience: JobExperience): String {
        return """
            \employment
                {${jobExperience.company.url}}
                {${jobExperience.company.displayName}}
                {${jobExperience.location}}
                {${jobExperience.roles.first().title}}
                {${makeWorkPeriod(jobExperience.roles.first().period)}}
        """.trimIndent()
    }

    private fun makeOtherRoles(jobExperience: JobExperience): String? {
        return if (jobExperience.roles.size == 1) {
            null
        } else {
            jobExperience.roles.drop(1).joinToString("\n\n") { role ->
                listOfNotNull(
                    makePosition(role),
                    makeBulletPoints(role.bulletPoints),
                ).joinToString("\n")
            }
        }
    }

    private fun makePosition(role: Role): String {
        return """
            \position
                {${role.title}}
                {${makeWorkPeriod(role.period)}}
        """.trimIndent()
    }

    private fun makeWorkPeriod(enrollmentPeriod: EnrollmentPeriod): String {
        val formatter = DateTimeFormatter.ofPattern("MMM. yyyy").withLocale(Locale.US)

        return "${formatter.format(enrollmentPeriod.start)} -- ${
            makeEndDate(
                formatter,
                enrollmentPeriod.end
            )
        }"
    }

    private fun makeEndDate(
        formatter: DateTimeFormatter,
        endDate: EnrollmentPeriod.EndDate,
    ): String {
        return when (endDate) {
            is EnrollmentPeriod.EndDate.Past -> formatter.format(endDate.date)
            EnrollmentPeriod.EndDate.Present -> "Present"
        }
    }

    private fun makeBulletPoints(bulletPoints: List<BulletPoint>): String? {
        return itemize(bulletPoints.map(::makeBulletPoint))
    }

    private fun makeBulletPoint(bulletPoints: BulletPoint): String {
        return bulletPoints.content.joinToString(separator = "") {
            when (it) {
                is BulletPointContent.PlainText -> it.displayName
                is BulletPointContent.Skill -> "\\textbf{${it.displayName}}"
            }
        }
    }

    private fun itemize(items: List<String>, itemsSeparator: String = "\n"): String? {
        return if (items.isEmpty()) null
        else "\\begin{itemize}\n${
            items.joinToString(itemsSeparator) { "\\item $it" }.reindent(1)
        }\n\\end{itemize}"
    }

    private fun makeProjectOrPublication(projectOrPublication: ProjectOrPublication): String {
        return """
            \project
                {${projectOrPublication.title.url}}
                {${projectOrPublication.title.displayName}}
                {${projectOrPublication.description}}
        """.trimIndent()
    }

    private fun makeDegree(degree: Degree): String {
        return """
            \employment
                {${degree.institution.url}}
                {${degree.institution.displayName}}
                {${degree.location}}
                {${degree.degree}}
                {${makeEduPeriod(degree.period)}}
        """.trimIndent()
    }

    private fun makeEduPeriod(enrollmentPeriod: EnrollmentPeriod): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy").withLocale(Locale.US)

        return "${formatter.format(enrollmentPeriod.start)} -- ${
            makeEndDate(
                formatter,
                enrollmentPeriod.end
            )
        }"
    }

    private val baseIndent = " ".repeat(4)
    private fun String.reindent(indentLevel: Int) =
        replaceIndent(baseIndent.repeat(indentLevel))
}
