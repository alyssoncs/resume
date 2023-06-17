package alysson.cirilo.resume.drivers.utils.indent

private const val indentWidth = 4
private val baseIndent = " ".repeat(indentWidth)
fun String.reindent(indentLevel: Int) =
    replaceIndent(baseIndent.repeat(indentLevel))
