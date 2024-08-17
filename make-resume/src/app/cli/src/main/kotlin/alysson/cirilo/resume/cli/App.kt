package alysson.cirilo.resume.cli

import java.io.File

fun main(args: Array<String>) {
    val (resumeFlavor, file, inputType) = parse(args)
    print(formatResume(file, inputType, resumeFlavor))
}

private fun formatResume(inputFile: File, inputType: InputType, resumeFlavor: Flavor): String {
    val serializedResume = inputFile.readText()
    val resume = inputType.deserialize(serializedResume)
    return resumeFlavor.convert(resume)
}
