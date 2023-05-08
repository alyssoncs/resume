package alysson.cirilo.resume.drivers

import alysson.cirilo.resume.entities.BulletPoint
import alysson.cirilo.resume.entities.BulletPointContent
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.EnrollmentPeriod
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.ProjectOrPublication
import alysson.cirilo.resume.entities.Resume
import alysson.cirilo.resume.infra.ResumeDriver
import java.time.format.DateTimeFormatter
import java.util.Locale

class LatexSoberResumeDriver : ResumeDriver {
    override fun convert(resume: Resume): String {
        val theResume = resume.reversedChronologically
        val toString = """
            %!TEX TS-program = xelatex
            %!TEX encoding = UTF-8 Unicode

            \documentclass[a4paper,11pt]{article}

            \usepackage{fontspec}
            \setmainfont{EB Garamond}
            \usepackage{fourier-orns}
            \usepackage{titlesec}
            \usepackage{enumitem}
            \usepackage[xetex,hidelinks]{hyperref}
            \usepackage{fancyhdr}
            \usepackage[
                a4paper, 
                top=1.6cm, 
                bottom=1.2cm, 
                left=1.2cm, 
                right=1.2cm, 
                includefoot, 
                footskip = 10.73pt,
                %showframe,
            ]{geometry}
            \usepackage{fontawesome}

            % page formatting
            \pagestyle{fancy}
            \fancyhf{} % clear all header and footer fields
            \fancyfoot{}
            \renewcommand{\headrulewidth}{0pt}
            \renewcommand{\footrulewidth}{0pt}

            \raggedbottom
            \raggedright
            \setlength{\tabcolsep}{0in}

            % section formatting
            \titleformat{\section}
                {\vspace{-2pt}\scshape\raggedright\large}
                {}
                {0em}
                {}[\titlerule\vspace{-5pt}]

            % list formatting
            \setlist[1]{leftmargin=*, itemsep=-3pt}
            \setlist[2]{topsep=-3pt, itemsep=-2.5pt, before*=\small}
            \renewcommand{\labelitemii}{\footnotesize${'$'}\circ${'$'}}

            % custom commands

            \newcommand{\iconhref}[2]{\href{#1}{#2~\textsuperscript{\tiny{\faExternalLink}}}}

            % companyUrl, companyName, location, positionName, positionDate
            \newcommand{\employment}[5]{
                \company{#1}{#2}{#3}
                \position{#4}{#5}
            }

            % companyUrl, companyName, location
            \newcommand{\company}[3]{
                \begin{tabular*}{0.97\textwidth}{l@{\extracolsep{\fill}}r}
                    \iconhref{#1}{\textbf{#2}} & {\small#3}\\
                \end{tabular*}
            }

            % positionName, positionDate
            \newcommand{\position}[2]{
                \begin{tabular*}{0.97\textwidth}{l@{\extracolsep{\fill}}r}
                    \textit{\small#1} & \textit{\small#2} \\
                \end{tabular*}
            }

            % projectUrl, projectName, projectDescription
            \newcommand{\project}[3]{
                \small{\textbf{\iconhref{#1}{#2}}{:~#3}}
            }

            \begin{document}
                % constants
                \newcommand{\name}{${theResume.name}}
                \newcommand{\mytitle}{{\huge\textbf{\name}}}
                \newcommand{\headline}{${theResume.headline.joinToString(separator = "{\\enskip\\starredbullet\\enskip}") { it }}}
                \newcommand{\email}
                    {\iconhref{${theResume.contactInformation.email.url}}{{\scriptsize\faEnvelope{}} ${theResume.contactInformation.email.displayName}}}
                \newcommand{\linkedin}
                    {\iconhref{${theResume.contactInformation.linkedin.url}}{\faLinkedin{} ${theResume.contactInformation.linkedin.displayName}}}
                \newcommand{\github}
                    {\iconhref{${theResume.contactInformation.github.url}}{\faGithub{} ${theResume.contactInformation.github.displayName}}}
                \newcommand{\address}
                    {\hspace{1pt}\iconhref{https://www.google.com/maps?q=+Maranhão,+Brazil}{\faMapMarker{}\hspace{1pt} Remote (UTC${'$'}-${'$'}3), Brazil}}

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

                \vspace{-10pt}

                \section{Experience}
        """.trimIndent() + "\n" +
                makeJobExperiences(theResume.jobExperiences).reindent(2) + "\n\n" +
        """
                    \vspace{-10pt}

                \section{Projects \textit{\&} Publications}
        """.reindent(1) + "\n" +
                makeProjectsAndPublications(theResume.projectsAndPublications).reindent(2) + "\n" +
        """
                    \vspace{-10pt}

                \section{Education}
        """.reindent(1) + "\n" +
                makeEducation(theResume.education).reindent(2) + "\n" +
        """
            \end{document}
        """.trimIndent()
        return toString
    }

    private fun makeJobExperiences(jobExperiences: List<JobExperience>): String {
        val jobExperiencesStr = jobExperiences.joinToString("\n") {
            makeJobExperience(it)
        }

        return "\\begin{itemize}\n${jobExperiencesStr.reindent(1)}\n\\end{itemize}"
    }

    private fun makeJobExperience(jobExperience: JobExperience): String {
        return "${makeFirstRole(jobExperience)}\n${makeOtherRoles(jobExperience)}"
    }

    private fun makeFirstRole(jobExperience: JobExperience): String {
        return """
                \item \employment
                    {${jobExperience.company.url}}
                    {${jobExperience.company.displayName}}
                    {${jobExperience.location}}
                    {${jobExperience.roles.first().title}}
                    {${makeWorkPeriod(jobExperience.roles.first().period)}}
                """.trimIndent() + "\n" +
                makeBulletPoints(jobExperience.roles.first().bulletPoints)
    }

    private fun makeOtherRoles(jobExperience: JobExperience): String {
        return if (jobExperience.roles.size == 1) {
            ""
        } else {
            jobExperience.roles.drop(1).joinToString("\n\n") { role ->
                """
                \position
                    {${role.title}}
                    {${makeWorkPeriod(role.period)}}
                """.trimIndent() + "\n" +
                        makeBulletPoints(role.bulletPoints)
            }
        }
    }

    private fun makeBulletPoints(bulletPoints: List<BulletPoint>): String {
        if (bulletPoints.isEmpty()) return ""

        val bulletPointsStr = bulletPoints.joinToString("\n") { makeBulletPoint(it) }
        return "\\begin{itemize}\n${bulletPointsStr.reindent(1)}\n\\end{itemize}"
    }

    private fun makeBulletPoint(bulletPoints: BulletPoint): String {
        return bulletPoints.content.joinToString(separator = "", prefix = "\\item ") {
            when (it) {
                is BulletPointContent.PlainText -> it.displayName
                is BulletPointContent.Skill -> "\\textbf{${it.displayName}}"
            }
        }
    }

    private fun makeProjectsAndPublications(projectsAndPublications: List<ProjectOrPublication>): String {
        if (projectsAndPublications.isEmpty()) return ""
        return "\\begin{itemize}\n${projectsAndPublications.joinToString("\n") { makeProjectOrPublication(it) }.reindent(1)}\n\\end{itemize}"
    }

    private fun makeProjectOrPublication(projectOrPublication: ProjectOrPublication): String {
        return """               
            \item \project
                {${projectOrPublication.title.url}}
                {${projectOrPublication.title.displayName}}
                {${projectOrPublication.description}}
        """.trimIndent()
    }

    private fun makeEducation(education: List<Degree>): String {
        if (education.isEmpty()) return ""
        return "\\begin{itemize}\n${education.joinToString("\n") { makeDegree(it) }.reindent(1)}\n\\end{itemize}"
    }

    private fun makeDegree(degree: Degree): String {
        return """                
            \item \employment
                {${degree.institution.url}}
                {${degree.institution.displayName}}
                {${degree.location}}
                {${degree.degree}}
                {${makeEduPeriod(degree.period)}}
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

    private fun makeEduPeriod(enrollmentPeriod: EnrollmentPeriod): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy").withLocale(Locale.US)

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

    private val baseIndent = " ".repeat(4)
    private fun String.reindent(indentLevel: Int) = replaceIndent(baseIndent.repeat(indentLevel))

}