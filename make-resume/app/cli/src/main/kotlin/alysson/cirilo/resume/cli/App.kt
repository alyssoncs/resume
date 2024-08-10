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

fun main(args: Array<String>) {
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
        shortName = null,
        fullName = "input-type",
        description = "The input type of [input] file",
    )

    argParser.parse(args)

    val inputType: InputType = getInputType(inputFile, inputTypeArg)

    print(formatResume(inputFile, inputType, resumeFlavor))
}

private fun getInputType(inputFile: String, inputTypeArg: InputType?): InputType {
    return if (inputTypeArg == null) {
        val extension = File(inputFile).extension
        InputType.fromExtension(extension) ?: error("Unknown extension: $extension")
    } else {
        inputTypeArg
    }
}

private fun formatResume(inputFile: String, inputType: InputType, resumeFlavor: Flavor): String {
    val serializedResume = File(inputFile).readText()
    val resume = inputType.deserialize(serializedResume)
    return resumeFlavor.driver.convert(resume)
}

private enum class Flavor {
    Awesome {
        override val driver: ResumeDriver get() = makeLatexAwesomeDriver()
    },
    Sober {
        override val driver: ResumeDriver get() = makeLatexSoberDriver()
    },
    Markdown {
        override val driver: ResumeDriver get() = makeMarkdownDriver()
    },
    ;

    abstract val driver: ResumeDriver
}

private enum class InputType(private val extension: String, val deserialize: (String) -> Resume) {
    Json(extension = "json", deserialize = ::deserializeJson),
    Yaml(extension = "yml", deserialize = ::deserializeYaml),
    ;

    companion object {
        fun fromExtension(extension: String): InputType? {
            return entries.find { inputType -> inputType.extension == extension }
        }
    }
}
