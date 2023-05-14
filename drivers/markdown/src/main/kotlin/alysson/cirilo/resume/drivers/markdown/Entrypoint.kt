package alysson.cirilo.resume.drivers.markdown

import alysson.cirilo.resume.drivers.utils.makeAgnosticDriver
import alysson.cirilo.resume.infra.ResumeDriver

fun makeMarkdownDriver(): ResumeDriver {
    return makeAgnosticDriver(MarkdownSyntaxFactory())
}
