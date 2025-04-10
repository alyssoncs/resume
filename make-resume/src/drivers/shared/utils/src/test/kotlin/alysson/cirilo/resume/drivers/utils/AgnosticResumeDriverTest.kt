package alysson.cirilo.resume.drivers.utils

import alysson.cirilo.resume.drivers.utils.date.educationDateFormatter
import alysson.cirilo.resume.drivers.utils.date.workDateFormatter
import alysson.cirilo.resume.entities.aResume
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class AgnosticResumeDriverTest {
    private val resumeDriver = makeDriver(FakeResumeBuilder(workDateFormatter, educationDateFormatter))

    @Test
    fun `should build resume correctly`() {
        val resume = aResume().build()

        val resumeStr = resumeDriver.convert(resume)

        resumeStr shouldBe """
            Alysson Cirilo
            Android Developer, Software Engineer

            > Contact info
                * alysson.cirilo@gmail.com (mailto:alysson.cirilo@gmail.com)
                * alyssoncirilo.com (alyssoncirilo.com)
                * linkedin.com/in/alysson-cirilo (https://www.linkedin.com/in/alysson-cirilo)
                * github.com/alyssoncs (https://www.github.com/alyssoncs)
                * Remote, Brazil (https://www.example.com)

            > Experience
            Experience @ Cool Company (https://www.coolcompany.com), Remote
                * Software Engineer (Apr. 2025 - Present)
                    - Worked with {kotlin}

            > Projects & Publications
                * cool project (https://www.github.com/alyssoncs/coolproject): a very cool project

            > Education
                * BSc. in Computer Science @ Top institution (https://www.example.com), Brazil 2020 - Present
        """.trimIndent()
    }
}
