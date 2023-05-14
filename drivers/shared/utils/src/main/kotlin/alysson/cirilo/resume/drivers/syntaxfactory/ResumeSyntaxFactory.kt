package alysson.cirilo.resume.drivers.syntaxfactory

import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.ProjectOrPublication

interface ResumeSyntaxFactory {

    fun addHeader(name: String, headline: List<String>, contactInformation: ContactInformation)

    fun startSection(name: String)

    fun makeExperiences(jobExperiences: List<JobExperience>)

    fun makeProjectsAndPublications(projectsAndPublications: List<ProjectOrPublication>)

    fun makeEducation(education: List<Degree>)

    fun create(): String
}