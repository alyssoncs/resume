package alysson.cirilo.resume.drivers.markdown

import alysson.cirilo.resume.drivers.utils.ResumeBuilderEntrypointTest
import alysson.cirilo.resume.infra.ResumeDriver

class MarkdownEntrypointTest : ResumeBuilderEntrypointTest() {
    override fun makeResumeDriver(): ResumeDriver = makeMarkdownDriver()
}
