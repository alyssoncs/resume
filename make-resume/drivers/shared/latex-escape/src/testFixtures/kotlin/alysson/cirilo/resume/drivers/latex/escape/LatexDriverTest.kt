package alysson.cirilo.resume.drivers.latex.escape

import alysson.cirilo.resume.entities.ResumeBuilder
import alysson.cirilo.resume.entities.aBulletPoint
import alysson.cirilo.resume.entities.aDegree
import alysson.cirilo.resume.entities.aJobExperience
import alysson.cirilo.resume.entities.aProject
import alysson.cirilo.resume.entities.aResume
import alysson.cirilo.resume.entities.aRole
import alysson.cirilo.resume.infra.ResumeDriver
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.stream.Stream

abstract class LatexDriverTest {

    private val locale = Locale.US
    private val workDateFormatter = DateTimeFormatter.ofPattern("MMM. yyyy").withLocale(locale)
    private val educationDateFormatter = DateTimeFormatter.ofPattern("MM. yyyy").withLocale(locale)

    private val driver by lazy { makeLatexDriver(workDateFormatter, educationDateFormatter) }

    abstract fun makeLatexDriver(
        workDateFormatter: DateTimeFormatter,
        educationDateFormatter: DateTimeFormatter,
    ): ResumeDriver

    @LatexEscapeParams
    fun `should escape name`(
        raw: String,
        escaped: String,
    ) {
        val resume = aResume().from(raw)

        val output = driver.convert(resume)

        output shouldContain escaped
    }

    @LatexEscapeParams
    fun `should escape headline`(
        raw: String,
        escaped: String,
    ) {
        val resume = aResume().withHeadline(raw)

        val output = driver.convert(resume)

        output shouldContain escaped
    }

    @LatexEscapeParams
    fun `should escape email`(
        raw: String,
        escaped: String,
    ) {
        val resume = aResume().with {
            email {
                displaying(raw)
            }
        }

        val output = driver.convert(resume)

        output shouldContain escaped
    }

    @LatexEscapeParams
    fun `should escape linkedin`(
        raw: String,
        escaped: String,
    ) {
        val resume = aResume().with {
            linkedin {
                displaying(raw)
            }
        }

        val output = driver.convert(resume)

        output shouldContain escaped
    }

    @LatexEscapeParams
    fun `should escape github`(
        raw: String,
        escaped: String,
    ) {
        val resume = aResume().with {
            github {
                displaying(raw)
            }
        }

        val output = driver.convert(resume)

        output shouldContain escaped
    }

    @LatexEscapeParams
    fun `should escape location`(
        raw: String,
        escaped: String,
    ) {
        val resume = aResume().with {
            location {
                displaying(raw)
            }
        }

        val output = driver.convert(resume)

        output shouldContain escaped
    }

    @LatexEscapeParams
    fun `should escape company`(
        raw: String,
        escaped: String,
    ) {
        val resume = aResume().with(
            aJobExperience().on(raw),
        )

        val output = driver.convert(resume)

        output shouldContain escaped
    }

    @LatexEscapeParams
    fun `should escape experience location`(
        raw: String,
        escaped: String,
    ) {
        val resume = aResume().with(
            aJobExperience().basedOn(raw),
        )

        val output = driver.convert(resume)

        output shouldContain escaped
    }

    @LatexEscapeParams
    fun `should escape role title`(
        raw: String,
        escaped: String,
    ) {
        val resume = aResume().with(
            aJobExperience().with(
                aRole().`as`(raw),
            ),
        )

        val output = driver.convert(resume)

        output shouldContain escaped
    }

    @LatexEscapeParams
    fun `should escape plain bullet point`(
        raw: String,
        escaped: String,
    ) {
        val resume = aResume().with(
            aJobExperience().with(
                aRole().with(
                    aBulletPoint().thatReads(raw),
                ),
            ),
        )

        val output = driver.convert(resume)

        output shouldContain escaped
    }

    @LatexEscapeParams
    fun `should escape skill bullet point`(
        raw: String,
        escaped: String,
    ) {
        val resume = aResume().with(
            aJobExperience().with(
                aRole().with(
                    aBulletPoint().withSkill(raw),
                ),
            ),
        )

        val output = driver.convert(resume)

        output shouldContain escaped
    }

    @LatexEscapeParams
    fun `should escape project title`(
        raw: String,
        escaped: String,
    ) {
        val resume = aResume().with(
            aProject().named(raw),
        )

        val output = driver.convert(resume)

        output shouldContain escaped
    }

    @LatexEscapeParams
    fun `should escape project description`(
        raw: String,
        escaped: String,
    ) {
        val resume = aResume().with(
            aProject().description(raw),
        )

        val output = driver.convert(resume)

        output shouldContain escaped
    }

    @LatexEscapeParams
    fun `should escape degree institution`(
        raw: String,
        escaped: String,
    ) {
        val resume = aResume().with(
            aDegree().at(raw),
        )

        val output = driver.convert(resume)

        output shouldContain escaped
    }

    @LatexEscapeParams
    fun `should escape degree location`(
        raw: String,
        escaped: String,
    ) {
        val resume = aResume().with(
            aDegree().on(raw),
        )

        val output = driver.convert(resume)

        output shouldContain escaped
    }

    @LatexEscapeParams
    fun `should escape degree title`(
        raw: String,
        escaped: String,
    ) {
        val resume = aResume().with(
            aDegree().tile(raw),
        )

        val output = driver.convert(resume)

        output shouldContain escaped
    }

    private fun ResumeDriver.convert(resume: ResumeBuilder) = convert(resume.build())

    @ParameterizedTest
    @ArgumentsSource(LatexEscapeCharacterProvider::class)
    annotation class LatexEscapeParams

    class LatexEscapeCharacterProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext): Stream<out Arguments?> {
            val escapeRules = mapOf(
                "&" to "\\&",
                "%" to "\\%",
            )

            return escapeRules
                .map { entry -> enclose(entry.key) to enclose(entry.value) }
                .map { pair -> Arguments.of(pair.first, pair.second) }
                .stream()
        }

        private fun enclose(s: String) = "word $s word"
    }
}
