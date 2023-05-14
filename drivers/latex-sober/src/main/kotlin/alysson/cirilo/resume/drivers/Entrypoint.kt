package alysson.cirilo.resume.drivers

import alysson.cirilo.resume.infra.ResumeDriver

fun makeLatexSoberDriver(): ResumeDriver = LatexSoberResumeDriver()
