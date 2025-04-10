package alysson.cirilo.resume.drivers.latexawesome

import alysson.cirilo.resume.drivers.utils.ResumeBuilderEntrypointTest
import alysson.cirilo.resume.infra.ResumeDriver

class LatexAwesomeEntrypointTest : ResumeBuilderEntrypointTest() {
    override fun makeResumeDriver(): ResumeDriver = makeLatexAwesomeDriver()
}
