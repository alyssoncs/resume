package alysson.cirilo.resume.cli

import alysson.cirilo.resume.drivers.latexawesome.makeLatexAwesomeDriver
import alysson.cirilo.resume.drivers.latexsober.makeLatexSoberDriver
import alysson.cirilo.resume.drivers.markdown.makeMarkdownDriver
import alysson.cirilo.resume.infra.ResumeDriver
import alysson.cirilo.resume.serialization.deserialize
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required
import java.io.File

fun main(args: Array<String>) {
    val argPasser = ArgParser(programName = "build-resume")

    val resumeFlavor by argPasser.option(
        ArgType.Choice<Flavor>(),
        shortName = "f",
        fullName = "flavor",
        description = "The flavor of the generated resume",
    ).required()

    val inputFile by argPasser.option(
        ArgType.String,
        shortName = "i",
        fullName = "input",
        description = "path to a json file with the resume data",
    ).required()
    argPasser.parse(args)

    print(formatResume(inputFile, resumeFlavor))
}

private fun formatResume(inputFile: String, resumeFlavor: Flavor): String {
    val jsonResume = File(inputFile).readText()
    val resume = deserialize(jsonResume)
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
    };

    abstract val driver: ResumeDriver
}
