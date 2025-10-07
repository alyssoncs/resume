package alysson.cirilo.resume.serialization.models

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SerializableResumeTest {
    @ParameterizedTest
    @CsvSource(
        value = [
            "''     , false",
            "1      , false",
            "01     , false",
            "01-24  , false",
            "2024-01, true",
            "1-2024 , false",
            "now    , false",
            "01-2024, true",
        ],
    )
    fun `enrollment period start date should match required pattern`(start: String, matchesPattern: Boolean) {
        val builder = aResumeDto()
            .with(aJobExperienceDto().with(aRoleDto().from(start)))

        assertMatchesPattern(matchesPattern, builder)
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "''     , false",
            "1      , false",
            "01     , false",
            "01-24  , false",
            "2024-01, false",
            "1-2024 , false",
            "now    , true",
            "01-2024, true",
        ],
    )
    fun `enrollment period end date should match required pattern`(end: String, matchesPattern: Boolean) {
        val builder = aResumeDto()
            .with(aJobExperienceDto().with(aRoleDto().upTo(end)))

        assertMatchesPattern(matchesPattern, builder)
    }

    private fun assertMatchesPattern(matchesPattern: Boolean, builder: SerializableResumeBuilder) {
        fun assertThrows(block: () -> Any?) = shouldThrow<IllegalArgumentException>(block)

        fun assertDoesNotThrow(block: () -> Any?) = shouldNotThrow<IllegalArgumentException>(block)

        val assert = if (matchesPattern) ::assertDoesNotThrow else ::assertThrows

        assert {
            builder.build()
        }
    }
}
