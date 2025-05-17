package alysson.cirilo.resume.cli

import alysson.cirilo.resume.drivers.latexawesome.makeLatexAwesomeDriver
import alysson.cirilo.resume.drivers.latexsober.makeLatexSoberDriver
import alysson.cirilo.resume.drivers.markdown.makeMarkdownDriver
import alysson.cirilo.resume.entities.Resume
import alysson.cirilo.resume.infra.ResumeDriver
import alysson.cirilo.resume.serialization.json.deserializeJson
import alysson.cirilo.resume.serialization.yaml.deserializeYaml
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.enum
import java.io.File

internal fun parse(args: Array<String>): Args {
    val (parser, getArgs) = makeArgParser()
    parser.main(args)
    return getArgs()
}

/**
 * Creates an argument parser and a function to retrieve the parsed arguments.
 *
 * This function is necessary for testing purposes because previously the `kotlinx-cli` library
 * used by this project was not designed with testability in mind.
 * By separating the parser creation and argument retrieval logic, it was easier
 * to mock or manipulate the parser during tests.
 *
 * Now that we are using `clikt`, we probably can get rid of this function.
 *
 * @return A pair containing the [CliktCommand] and a lambda function to retrieve [Args].
 */
internal fun makeArgParser(): Pair<CliktCommand, () -> Args> {
    val cliktParser = CliktParser()
    val parser = cliktParser

    val getArgs = {
        val file = File(parser.inputFile)
        val inputType = getInputType(file, parser.inputType)
        Args(parser.flavor, file, inputType)
    }

    return Pair(parser, getArgs)
}

internal class CliktParser : CliktCommand("cli-uber") {
    val flavor: Flavor by option("-f", "--flavor")
        .enum<Flavor>()
        .required()
    val inputFile: String by option("-i", "--input")
        .required()
    val inputType: InputType? by option("--input-type")
        .enum<InputType>()

    override fun run() {
        // no-op
    }
}

private fun getInputType(inputFile: File, inputTypeArg: InputType?): InputType {
    return if (inputTypeArg == null) {
        val extension = inputFile.extension
        InputType.fromExtension(extension) ?: error("Unknown extension: $extension")
    } else {
        inputTypeArg
    }
}

internal data class Args(
    val flavor: Flavor,
    val file: File,
    val inputType: InputType,
)

internal enum class Flavor(driver: ResumeDriver) : ResumeDriver by driver {
    Awesome(driver = makeLatexAwesomeDriver()),
    Sober(driver = makeLatexSoberDriver()),
    Markdown(driver = makeMarkdownDriver()),
}

internal enum class InputType(val extension: String, val deserialize: (String) -> Resume) {
    Json(extension = "json", deserialize = ::deserializeJson),
    Yaml(extension = "yml", deserialize = ::deserializeYaml),
    ;

    companion object {
        fun fromExtension(extension: String): InputType? {
            return entries.find { inputType -> inputType.extension == extension }
        }
    }
}
