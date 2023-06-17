package alysson.cirilo.resume.drivers.utils.indent

private const val INDENT_WIDTH = 4
private val baseIndent = " ".repeat(INDENT_WIDTH)
fun String.reindent(indentLevel: Int) =
    replaceIndent(baseIndent.repeat(indentLevel))
