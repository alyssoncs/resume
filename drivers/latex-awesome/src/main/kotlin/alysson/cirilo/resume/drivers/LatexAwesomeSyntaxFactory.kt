package alysson.cirilo.resume.drivers

import alysson.cirilo.resume.entities.BulletPoint
import alysson.cirilo.resume.entities.BulletPointContent
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.EnrollmentPeriod
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.ProjectOrPublication
import java.time.format.DateTimeFormatter
import java.util.Locale

class LatexAwesomeSyntaxFactory(
    resource: String,
    private val firstNamePlaceholder: String,
    private val lastNamePlaceholder: String,
    private val headlinePlaceholder: String,
    private val addressPlaceholder: String,
    private val emailPlaceholder: String,
    private val githubPlaceholder: String,
    private val linkedinPlaceholder: String,
    private val contentPlaceholder: String,
) {

    private var output = ""
    private var currentIndent = 0
    private var sectionIndent: Int? = null
    private val template: String by lazy {
        javaClass.getResource(resource)!!.readText()
    }

    private var firstName: String? = null
    private var lastName: String? = null
    private var headline: List<String> = emptyList()
    private var contactInformation: ContactInformation? = null

    private fun updateOutput(newContent: String) {
        output += newContent
    }

    fun addHeader(name: String, headline: List<String>, contactInformation: ContactInformation) {
        firstName = name.substringBefore(' ')
        lastName = name.substringAfter(' ')
        this.headline = headline
        this.contactInformation = contactInformation
    }

    fun startSection(name: String) {
        if (sectionIndent == null) {
            sectionIndent = currentIndent
        }
        sectionIndent?.let { theSectionIndent ->
            updateOutput(
                "\n" +
                        """
                        \cvsection{$name}
                        """.reindent(theSectionIndent) + "\n"
            )
            currentIndent = theSectionIndent.inc()
        }
    }

    fun makeExperiences(jobExperiences: List<JobExperience>) {
        updateOutput(
            makeJobExperiences(jobExperiences).reindent(currentIndent) + "\n"
        )
    }

    fun makeProjectsAndPublications(projectsAndPublications: List<ProjectOrPublication>) {
        val projectsAndPublicationsStr = if (projectsAndPublications.isEmpty())
            ""
        else
            """
            \vspace{-14pt}
            \begin{cventries}
                \cventry
                    {}
                    {}
                    {}
                    {}
                    {
        """.trimIndent() + "\n" +
                    itemize(projectsAndPublications.map(::makeProjectOrPublication)).reindent(3) + "\n" +
                    """
                            }
                    \end{cventries}
                """.trimIndent()

        updateOutput(
            projectsAndPublicationsStr.reindent(currentIndent) + "\n"
        )
    }

    fun makeEducation(education: List<Degree>) {
        updateOutput(
            if (education.isEmpty()) ""
            else
                ("\\vspace{-2pt}\\begin{cventries}\n" +
                        education.joinToString("\n") { makeDegree(it) }.reindent(1) +
                        "\n\\end{cventries}").reindent(currentIndent)
        )
    }

    override fun toString(): String {
        return template
            .replace(firstNamePlaceholder, firstName.orEmpty())
            .replace(lastNamePlaceholder, lastName.orEmpty())
            .replace(headlinePlaceholder, headline.joinToString(separator = "{\\enskip\\starredbullet\\enskip}") { it })
            .replace(addressPlaceholder, contactInformation?.location?.displayName.orEmpty())
            .replace(emailPlaceholder, contactInformation?.email?.displayName.orEmpty())
            .replace(githubPlaceholder, contactInformation?.github?.displayName.orEmpty())
            .replace(linkedinPlaceholder, contactInformation?.linkedin?.displayName.orEmpty())
            .replace(contentPlaceholder, output.reindent(1))
    }

    private fun makeJobExperiences(jobExperiences: List<JobExperience>): String {
        return "\\vspace{-6pt}\\begin{cventries}" + "\n" +
                jobExperiences.joinToString("\n\n") { makeJobExperience(it) }.reindent(1) +
                "\n\\end{cventries}\n"
    }

    private fun makeJobExperience(jobExperience: JobExperience): String {
        return "${makeFirstRole(jobExperience)}${makeOtherRoles(jobExperience)}"
    }

    private fun makeFirstRole(jobExperience: JobExperience): String {
        return """
                \cventry
                    {${jobExperience.roles.first().title}}
                    {${jobExperience.company.displayName}}
                    {${jobExperience.location}}
                    {${makeWorkPeriod(jobExperience.roles.first().period)}}
                    {
                """.trimIndent() + "\n" +
                makeBulletPoints(jobExperience.roles.first().bulletPoints).reindent(2) + "\n" +
                "}".reindent(1)

    }

    private fun makeOtherRoles(jobExperience: JobExperience): String {
        return if (jobExperience.roles.size == 1) {
            ""
        } else {
            jobExperience.roles.drop(1).joinToString("\n\n", prefix = "\n\n") { role ->
                """
                \cventry
                    {${role.title}}
                    {}
                    {}
                    {${makeWorkPeriod(role.period)}}
                    {
                """.trimIndent() + "\n" +
                        makeBulletPoints(role.bulletPoints).reindent(2) + "\n" +
                        "}".reindent(1)
            }
        }
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

    private fun makeBulletPoints(bulletPoints: List<BulletPoint>): String {
        if (bulletPoints.isEmpty()) return ""

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

    private fun itemize(items: List<String>): String {
        return "\\begin{cvitems}\n${
            items.joinToString("\n") { "\\item $it" }.reindent(1)
        }\n\\end{cvitems}"
    }

    private fun makeProjectOrPublication(projectOrPublication: ProjectOrPublication): String {
        return "\\textbf{${projectOrPublication.title.displayName}:} ${projectOrPublication.description}"
    }

    private fun makeDegree(degree: Degree): String {
        return """
            \cventry
                {${degree.degree}}
                {${degree.institution.displayName}}
                {${degree.location}}
                {${makeEduPeriod(degree.period)}}
                {}
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
