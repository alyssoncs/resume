package alysson.cirilo.resume.cli

import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.main
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.ValueSource

internal class ArgParsingTest {
    private val pair = makeArgParser()
    private val parser = pair.first
    private val getArgs = pair.second

    @BeforeEach
    fun setUp() {
        parser.context {
            exitProcess = { s -> error("mocked error > $s") }
        }
    }

    @Test
    fun `should throw an error when no args are provided`() {
        shouldThrow<Throwable> {
            doParse()
        }
    }

    @Test
    fun `should throw an error when no flavor is provided`() {
        shouldThrow<Throwable> {
            doParse("-i", "input.json")
        }
    }

    @Test
    fun `should throw an error when no input file is provided`() {
        shouldThrow<Throwable> {
            doParse("-i", "input.json")
        }
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
        shouldThrow<Throwable> {
            doParse("-i", "input.json", "-f", "unknown")
        }
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
        shouldThrow<Throwable> {
            doParse("-i", "input.xml", "-f", "sober")
        }
    }

    @Test
    fun `overrides input type from input-type arg`() {
        val args = doParse("-i", "input.json", "-f", "sober", "--input-type", "yaml")

        args.inputType shouldBe InputType.Yaml
    }

    @Test
    fun `fails on unknown input-type`() {
        shouldThrow<Throwable> {
            doParse("-i", "input.json", "-f", "sober", "--input-type", "xml")
        }
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
        parser.main(args)
        return getArgs()
    }
}
