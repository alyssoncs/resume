package alysson.cirilo.resume.infra

import alysson.cirilo.resume.entities.Resume

interface ResumeDriver {
    fun convert(resume: Resume): String
}
