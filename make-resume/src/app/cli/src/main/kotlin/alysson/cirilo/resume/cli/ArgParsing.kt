package alysson.cirilo.resume.cli

import alysson.cirilo.resume.drivers.latexawesome.makeLatexAwesomeDriver
import alysson.cirilo.resume.drivers.latexsober.makeLatexSoberDriver
import alysson.cirilo.resume.drivers.markdown.makeMarkdownDriver
import alysson.cirilo.resume.entities.Resume
import alysson.cirilo.resume.infra.ResumeDriver
import alysson.cirilo.resume.serialization.json.deserializeJson
import alysson.cirilo.resume.serialization.yaml.deserializeYaml
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required
import java.io.File

internal fun parse(args: Array<String>): Args {
    val (parser, getArgs) = makeArgParser()
    parser.parse(args)
    return getArgs()
}

/**
 * Creates an argument parser and a function to retrieve the parsed arguments.
 *
 * This function is necessary for testing purposes because the `kotlinx-cli` library
 * does not provide a straightforward way to test argument parsing directly. By
 * separating the parser creation and argument retrieval logic, it becomes easier
 * to mock or manipulate the parser during tests.
 *
 * @return A pair containing the [ArgParser] and a lambda function to retrieve [Args].
 */
internal fun makeArgParser(): Pair<ArgParser, () -> Args> {
    val argParser = ArgParser(programName = "cli-uber")

    val resumeFlavor by argParser.option(
        ArgType.Choice<Flavor>(),
        shortName = "f",
        fullName = "flavor",
        description = "The flavor of the generated resume",
    ).required()

    val inputFile by argParser.option(
        ArgType.String,
        shortName = "i",
        fullName = "input",
        description = "Path to a file with the resume data",
    ).required()

    val inputTypeArg by argParser.option(
        ArgType.Choice<InputType>(),
        fullName = "input-type",
        description = "The input type of [input] file",
    )

    val getArgs = {
        val file = File(inputFile)
        val inputType = getInputType(file, inputTypeArg)
        Args(resumeFlavor, file, inputType)
    }

    return Pair(argParser, getArgs)
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
