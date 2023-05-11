package alysson.cirilo.resume.entities.test.databuilders

import alysson.cirilo.resume.entities.Degree

class DegreeBuilder {
    private var institutionBuilder: LinkedInformationBuilder = aLinkedInfo()
        .displaying("Top institution")
        .linkingTo("https://www.example.com")

    private var location: String = "Brazil"

    private var degree: String = "BSc. in Computer Science"

    private var periodBuilder: EnrollmentPeriodBuilder = period()
        .from(5, 2020)
        .upToNow()

    fun at(institutionBuilder: LinkedInformationBuilder) = builderMethod {
        this.institutionBuilder = institutionBuilder
    }

    fun on(location: String) = builderMethod {
        this.location = location
    }

    fun tile(degree: String) = builderMethod {
        this.degree = degree
    }

    fun during(periodBuilder: EnrollmentPeriodBuilder) = builderMethod {
        this.periodBuilder = periodBuilder
    }

    fun build(): Degree {
        return Degree(
            institution = institutionBuilder.build(),
            location = location,
            degree = degree,
            period = periodBuilder.build(),
        )
    }
}

fun aDegree() = DegreeBuilder()

fun institution(name: String, urlSpec: String) = aLinkedInfo()
    .displaying(name)
    .linkingTo(urlSpec)