package alysson.cirilo.resume.drivers.utils

import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.ProjectOrPublication

interface ResumeBuilder {

    fun addHeader(name: String, headline: List<String>, contactInformation: ContactInformation): ResumeBuilder

    fun startSection(name: String): ResumeBuilder

    fun makeExperiences(jobExperiences: List<JobExperience>): ResumeBuilder

    fun makeProjectsAndPublications(projectsAndPublications: List<ProjectOrPublication>): ResumeBuilder

    fun makeEducation(education: List<Degree>): ResumeBuilder

    fun build(): String
}
