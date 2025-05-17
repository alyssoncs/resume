package alysson.cirilo.resume.cli

import kotlin.system.exitProcess

fun main(args: Array<String>) {
    app(args)
}

internal fun app(args: Array<String>, exitProcess: (Int) -> Unit = ::exitProcess) {
    parse(args)
        .onSuccess {
            print(formatResume(it))
        }
        .onFailure {
            System.err.println(it.message)
            exitProcess(1)
        }
}

private fun formatResume(args: Args): String {
    val serializedResume = args.file.readText()
    val resume = args.inputType.deserialize(serializedResume)
    return args.flavor.convert(resume)
}
