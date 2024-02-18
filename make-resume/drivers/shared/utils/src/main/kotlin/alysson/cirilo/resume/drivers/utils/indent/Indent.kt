package alysson.cirilo.resume.drivers.utils.indent

fun String.reindent(indentLevel: Int) = replaceIndent(baseIndent.repeat(indentLevel))

private const val INDENT_WIDTH = 4
private const val SPACE = " "
private val baseIndent = SPACE.repeat(INDENT_WIDTH)
