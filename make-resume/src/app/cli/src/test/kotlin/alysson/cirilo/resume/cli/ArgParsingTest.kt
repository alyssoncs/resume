package alysson.cirilo.resume.cli

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldContainIgnoringCase
import io.kotest.matchers.string.shouldNotContain
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.ValueSource

internal class ArgParsingTest {
    @Test
    fun `should throw an error when no args are provided`() {
        val error = shouldThrow<Throwable> {
            doParse()
        }

        assertErrorMessage(error, ErrorReason.MissingOption(Option.Flavor, Option.InputFile))
    }

    @Test
    fun `should throw an error when no flavor is provided`() {
        val error = shouldThrow<Throwable> {
            doParse("-i", "input.json")
        }

        assertErrorMessage(error, ErrorReason.MissingOption(Option.Flavor))
    }

    @Test
    fun `should throw an error when no input file is provided`() {
        val error = shouldThrow<Throwable> {
            doParse("-f", "sober")
        }

        assertErrorMessage(error, ErrorReason.MissingOption(Option.InputFile))
    }

    @Test
    fun `parses if input and flavor are provided`() {
        shouldNotThrow<Throwable> {
            doParse("-i", "input.json", "-f", "sober")
        }
    }

    @ParameterizedTest
    @EnumSource(Flavor::class)
    fun `parses known flavors`(flavor: Flavor) {
        val args = doParse("-i", "input.json", "-f", flavor.name.lowercase())

        args.flavor shouldBe flavor
    }

    @Test
    fun `fails on unknown flavors`() {
        val error = shouldThrow<Throwable> {
            doParse("-i", "input.json", "-f", "unknown")
        }

        assertErrorMessage(error, ErrorReason.InvalidOption(Option.Flavor, "unknown"))
    }

    @ParameterizedTest
    @EnumSource(InputType::class)
    fun `parses known inputs from extension`(inputType: InputType) {
        val inputFile = "input.${inputType.extension}"

        val args = doParse("-i", inputFile, "-f", "sober")

        args.inputType shouldBe inputType
    }

    @Test
    fun `fails on unknown input file extension`() {
        val error = shouldThrow<Throwable> {
            doParse("-i", "input.xml", "-f", "sober")
        }

        error.message shouldBe "Unknown extension: xml"
    }

    @Test
    fun `overrides input type from input-type arg`() {
        val args = doParse("-i", "input.json", "-f", "sober", "--input-type", "yaml")

        args.inputType shouldBe InputType.Yaml
    }

    @Test
    fun `fails on unknown input-type`() {
        val error = shouldThrow<Throwable> {
            doParse("-i", "input.json", "-f", "sober", "--input-type", "xml")
        }

        assertErrorMessage(error, ErrorReason.InvalidOption(Option.InputType, "xml"))
    }

    @Test
    fun `parses input file`() {
        val inputFile = "input.json"

        val args = doParse("-i", inputFile, "-f", "sober")

        args.file.path shouldBe inputFile
    }

    @ParameterizedTest
    @ValueSource(strings = ["-i", "--input"])
    fun `input file accepts long mode`(inputOption: String) {
        val inputFile = "input.json"

        val args = doParse(inputOption, inputFile, "-f", "sober")

        args.inputType shouldBe InputType.Json
        args.file.path shouldBe inputFile
    }

    @ParameterizedTest
    @ValueSource(strings = ["-f", "--flavor"])
    fun `flavor accepts long mode`(flavorOption: String) {
        val args = doParse("-i", "input.json", flavorOption, "sober")

        args.flavor shouldBe Flavor.Sober
    }

    private fun doParse(vararg args: String): Args {
        return parse(args.toList().toTypedArray()).getOrThrow()
    }

    private fun assertErrorMessage(error: Throwable, reason: ErrorReason) {
        error.message shouldContainIgnoringCase "usage"
        error.message shouldContain "cli-uber"
        when (reason) {
            is ErrorReason.MissingOption -> {
                error.message shouldContain "missing"
                reason.options.forEach { option ->
                    error.message shouldContain option.regex
                }
                reason.options.others().forEach { option ->
                    error.message shouldNotContain option.regex
                }
            }

            is ErrorReason.InvalidOption -> {
                error.message shouldContain "invalid"
                error.message shouldContain reason.option.regex
                error.message shouldContain reason.invalidValue
            }
        }
    }

    sealed interface ErrorReason {
        data class MissingOption(val options: List<Option>) : ErrorReason {
            constructor(option: Option) : this(listOf(option))
            constructor(vararg options: Option) : this(options.toList())
        }

        data class InvalidOption(val option: Option, val invalidValue: String) : ErrorReason
    }

    enum class Option(long: String, short: String? = null) {
        Flavor("--flavor", "-f"),
        InputFile("--input", "-i"),
        InputType("--input-type"),
        ;

        val regex = Regex("(${listOfNotNull(long, short).joinToString("|")})")
    }

    private fun List<Option>.others(): List<Option> {
        return Option.entries.filter { it !in this }
    }
}
