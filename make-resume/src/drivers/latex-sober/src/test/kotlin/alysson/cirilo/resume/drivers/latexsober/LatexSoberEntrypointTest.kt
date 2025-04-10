package alysson.cirilo.resume.drivers.latexsober

import alysson.cirilo.resume.drivers.utils.ResumeBuilderEntrypointTest
import alysson.cirilo.resume.infra.ResumeDriver

class LatexSoberEntrypointTest : ResumeBuilderEntrypointTest() {
    override fun makeResumeDriver(): ResumeDriver = makeLatexSoberDriver()
}
