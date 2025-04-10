package alysson.cirilo.resume.entities

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.beEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.should
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ResumeTest {

    @TestFactory
    fun `should not accept blank name`(): List<DynamicTest> {
        val blankNames = listOf("", " ", "\t")

        return blankNames.map { blankName ->
            dynamicTest("a resume with name = \"$blankName\" cannot be created") {
                shouldThrow<IllegalArgumentException> {
                    aResume().from(blankName).build()
                }
            }
        }
    }

    @Test
    fun `should not accept empty headline`() {
        shouldThrow<IllegalArgumentException> {
            aResume().withEmptyHeadline().build()
        }
    }

    @Test
    fun `should not accept job experience without a role`() {
        shouldThrow<IllegalArgumentException> {
            aResume().with(aJobExperience().withNoRoles()).build()
        }
    }

    @Test
    fun `should not accept bullet point without content`() {
        shouldThrow<IllegalArgumentException> {
            aResume().with(anEmptyBulletPoint()).build()
        }
    }

    @Test
    fun `should not accept blank skill bullet point content`() {
        shouldThrow<IllegalArgumentException> {
            aResume().with(
                anEmptyBulletPoint()
                    .appendText("worked with")
                    .appendSkill("  "),
            ).build()
        }
    }

    @Test
    fun `should not accept empty plaint text bullet point content`() {
        shouldThrow<IllegalArgumentException> {
            aResume().with(
                anEmptyBulletPoint()
                    .appendText("  ")
                    .appendSkill("kotlin"),
            ).build()
        }
    }

    @Test
    fun `resume with no experience should have no skills`() {
        val resume = aResume().withNoExperiences().build()

        resume.skills should beEmpty()
    }

    @Test
    fun `resume with no bullet points should have no skills`() {
        val resume = aResume().with(aJobExperience().with(aRole().withNoBulletPoints())).build()

        resume.skills should beEmpty()
    }

    @Test
    fun `resume with bullet point with no skills should have no skills`() {
        val resume = aResume().with(aBulletPoint().thatReads("Achieved result X.")).build()

        resume.skills should beEmpty()
    }

    @Test
    fun `resume with skill on bullet point should have skill`() {
        val resume = aResume().with(aBulletPoint().withSkill("kotlin")).build()

        resume.skills shouldHaveSize 1
        resume.skills.shouldContainExactly("kotlin")
    }

    @Test
    fun `resume with skills on bullet points should have the same skills`() {
        val resume = aResume()
            .with(
                aJobExperience()
                    .with(
                        aRole()
                            .with(
                                anEmptyBulletPoint()
                                    .appendText("worked with ")
                                    .appendSkill("kotlin"),
                            )
                            .and(
                                anEmptyBulletPoint()
                                    .appendText("created layouts in ")
                                    .appendSkill("jetpack compose"),
                            ),
                    )
                    .and(
                        aRole()
                            .with(aBulletPoint().thatReads("no skills here"))
                            .and(
                                anEmptyBulletPoint()
                                    .appendText("worked with ")
                                    .appendSkill("retrofit"),
                            ),
                    ),
            )
            .and(
                aJobExperience()
                    .with(
                        aRole().with(aBulletPoint().withSkill("android")),
                    ),
            ).build()

        resume.skills shouldHaveSize 4
        resume.skills.shouldContainExactly(
            "kotlin",
            "jetpack compose",
            "retrofit",
            "android",
        )
    }

    @Test
    fun `resume with duplicated skills on bullet points should have the unique skills`() {
        val resume = aResume()
            .with(
                aJobExperience()
                    .with(
                        aRole()
                            .with(
                                anEmptyBulletPoint()
                                    .appendText("worked with ")
                                    .appendSkill("kotlin"),
                            )
                            .and(
                                anEmptyBulletPoint()
                                    .appendText("created layouts in ")
                                    .appendSkill("jetpack compose")
                                    .appendText("using ")
                                    .appendSkill("kotlin"),
                            ),
                    )
                    .and(
                        aRole()
                            .with(aBulletPoint().thatReads("no skills here"))
                            .and(
                                anEmptyBulletPoint()
                                    .appendText("worked with ")
                                    .appendSkill("retrofit"),
                            ),
                    ),
            )
            .build()

        resume.skills shouldHaveSize 3
        resume.skills.shouldContainExactly(
            "kotlin",
            "jetpack compose",
            "retrofit",
        )
    }

    @Test
    fun `job experiences can be reversed`() {
        val chronologicalExperiences = listOf(
            aJobExperience().on("Meta"),
            aJobExperience().on("iFood"),
            aJobExperience().on("Microsoft"),
        )

        val reversedResume = aResume()
            .with(chronologicalExperiences).build().reversedChronologically

        reversedResume.jobExperiences.map { it.company.displayName }
            .shouldContainExactly("Microsoft", "iFood", "Meta")
    }

    @Test
    fun `roles can be reversed`() {
        val chronologicalExperiences = listOf(
            aJobExperience()
                .with(aRole().`as`("SWE 1"))
                .and(aRole().`as`("SWE 2")),
            aJobExperience()
                .with(aRole().`as`("SWE 3")),
        )

        val reversedResume = aResume()
            .with(chronologicalExperiences).build().reversedChronologically

        val titles = reversedResume.jobExperiences.flatMap { it.roles }.map { it.title }
        titles.shouldContainExactly("SWE 3", "SWE 2", "SWE 1")
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "\t", "\n"])
    fun `linked information should have display name`(blank: String) {
        shouldThrow<IllegalArgumentException> {
            aLinkedInfo().displaying(blank).build()
        }
    }

    private fun ResumeBuilder.with(aBulletPoint: BulletPointBuilder) =
        this.with(aJobExperience().with(aRole().with(aBulletPoint)))
}
