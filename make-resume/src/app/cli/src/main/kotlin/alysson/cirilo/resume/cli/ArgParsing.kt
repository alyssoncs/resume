package alysson.cirilo.resume.cli

import alysson.cirilo.resume.drivers.latexawesome.makeLatexAwesomeDriver
import alysson.cirilo.resume.drivers.latexsober.makeLatexSoberDriver
import alysson.cirilo.resume.drivers.markdown.makeMarkdownDriver
import alysson.cirilo.resume.entities.Resume
import alysson.cirilo.resume.infra.ResumeDriver
import alysson.cirilo.resume.serialization.json.deserializeJson
import alysson.cirilo.resume.serialization.yaml.deserializeYaml
import com.github.ajalt.clikt.core.CliktError
import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.parse
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.enum
import java.io.File

@Suppress("ReturnCount")
internal fun parse(args: Array<String>): Result<Args> {
    val parser = CliktParser()
    try {
        parser.parse(args)
    } catch (e: CliktError) {
        return Result.failure(Throwable(parser.getFormattedHelp(e)))
    }

    val file = File(parser.inputFile)
    val inputType = getInputType(file, parser.inputType)
        .onFailure { return Result.failure(it) }
    return Result.success(Args(parser.flavor, file, inputType.getOrThrow()))
}

private class CliktParser : NoOpCliktCommand("cli-uber") {
    val flavor: Flavor by option("-f", "--flavor")
        .enum<Flavor>()
        .required()
    val inputFile: String by option("-i", "--input")
        .required()
    val inputType: InputType? by option("--input-type")
        .enum<InputType>()
}

private fun getInputType(inputFile: File, inputTypeArg: InputType?): Result<InputType> {
    return if (inputTypeArg == null) {
        val extension = inputFile.extension
        InputType.fromExtension(extension)?.let { Result.success(it) }
            ?: Result.failure(Throwable("Unknown extension: $extension"))
    } else {
        Result.success(inputTypeArg)
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
