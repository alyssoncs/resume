package alysson.cirilo.resume.entities

import alysson.cirilo.resume.entities.testbuilders.BulletPointBuilder
import alysson.cirilo.resume.entities.testbuilders.ResumeBuilder
import alysson.cirilo.resume.entities.testbuilders.aBulletPoint
import alysson.cirilo.resume.entities.testbuilders.aJobExperience
import alysson.cirilo.resume.entities.testbuilders.aResume
import alysson.cirilo.resume.entities.testbuilders.aRole
import alysson.cirilo.resume.entities.testbuilders.anEmptyBulletPoint
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows

class ResumeTest {

    @TestFactory
    fun `should not accept blank name`(): List<DynamicTest> {
        val blankNames = listOf("", " ", "\t")

        return blankNames.map { blankName ->
            dynamicTest("a resume with name = \"$blankName\" cannot be created") {
                assertThrows<IllegalArgumentException> {
                    aResume().withName(blankName).build()
                }
            }
        }
    }

    @Test
    fun `should not accept empty headline`() {
        assertThrows<IllegalArgumentException> {
            aResume().withEmptyHeadline().build()
        }
    }

    @Test
    fun `should not accept job experience without a role`() {
        assertThrows<IllegalArgumentException> {
            aResume().with(aJobExperience().withNoRole()).build()
        }
    }

    @Test
    fun `should not accept bullet point without content`() {
        assertThrows<IllegalArgumentException> {
            aResume().with(aBulletPoint().withNoContent()).build()
        }
    }

    @Test
    fun `resume with no experience should have no skills`() {
        val resume = aResume().withNoExperience().build()

        assertThat(resume.skills).isEmpty()
    }

    @Test
    fun `resume with no bullet points should have no skills`() {
        val resume = aResume().with(aJobExperience().with(aRole().withNoBulletPoints())).build()

        assertThat(resume.skills).isEmpty()
    }

    @Test
    fun `resume with bullet point with no skills should have no skills`() {
        val resume = aResume().with(aBulletPoint().thatReads("Achieved result X.")).build()

        assertThat(resume.skills).isEmpty()
    }

    @Test
    fun `resume with skill on bullet point should have skill`() {
        val resume = aResume().with(aBulletPoint().withSkill("kotlin")).build()

        assertThat(resume.skills).hasSize(1)
        assertThat(resume.skills).containsExactly(ProfessionalSkill("kotlin"))
    }

    @Test
    fun `resume with skills on bullet points should have skills on the same order`() {
        val resume = aResume()
            .withNoExperience()
            .append(
                aJobExperience()
                    .withNoRole()
                    .append(
                        aRole()
                            .withNoBulletPoints()
                            .append(
                                anEmptyBulletPoint()
                                    .appendText("worked with ")
                                    .appendSkill("kotlin"),
                            )
                            .append(
                                anEmptyBulletPoint()
                                    .appendText("created layouts in ")
                                    .appendSkill("jetpack compose"),
                            ),
                    )
                    .append(
                        aRole()
                            .withNoBulletPoints()
                            .append(aBulletPoint().thatReads("no skills here"))
                            .append(
                                anEmptyBulletPoint()
                                    .appendText("worked with ")
                                    .appendSkill("retrofit"),
                            ),
                    )
            )
            .append(
                aJobExperience()
                    .with(
                        aRole().with(aBulletPoint().withSkill("android"))
                    ),
            ).build()

        assertThat(resume.skills).hasSize(4)
        assertThat(resume.skills).containsExactly(
            ProfessionalSkill("kotlin"),
            ProfessionalSkill("jetpack compose"),
            ProfessionalSkill("retrofit"),
            ProfessionalSkill("android"),
        ).inOrder()
    }

    private fun ResumeBuilder.with(aBulletPoint: BulletPointBuilder) =
        this.with(aJobExperience().with(aRole().with(aBulletPoint)))
}
