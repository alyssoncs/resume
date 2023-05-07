package alysson.cirilo.resume.infra

import alysson.cirilo.resume.entities.Resume

interface ResumeDriver {
    suspend fun convert(resume: Resume): String
}
