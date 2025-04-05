package alysson.cirilo.resume.drivers.markdown

import alysson.cirilo.resume.drivers.utils.date.educationDateFormatter
import alysson.cirilo.resume.drivers.utils.date.workDateFormatter
import alysson.cirilo.resume.drivers.utils.makeDriver
import alysson.cirilo.resume.infra.ResumeDriver

fun makeMarkdownDriver(): ResumeDriver {
    return makeDriver(MarkdownResumeBuilder(workDateFormatter, educationDateFormatter))
}
