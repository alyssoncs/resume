package alysson.cirilo.resume.entities.test.databuilders

import alysson.cirilo.resume.entities.Degree

class DegreeBuilder private constructor(
    private val institutionBuilder: LinkedInformationBuilder,
    private val location: String,
    private val degree: String,
    private val periodBuilder: EnrollmentPeriodBuilder,
) {
    constructor() : this(
        institutionBuilder = aLinkedInfo()
            .displaying("Top institution")
            .linkingTo("https://www.example.com"),
        location = "Brazil",
        degree = "BSc. in Computer Science",
        periodBuilder = period().from(5, 2020).upToNow(),
    )

    fun at(institutionBuilder: LinkedInformationBuilder) =
        DegreeBuilder(institutionBuilder, location, degree, periodBuilder)

    fun on(location: String) =
        DegreeBuilder(institutionBuilder, location, degree, periodBuilder)

    fun tile(degree: String) =
        DegreeBuilder(institutionBuilder, location, degree, periodBuilder)

    fun during(periodBuilder: EnrollmentPeriodBuilder) =
        DegreeBuilder(institutionBuilder, location, degree, periodBuilder)

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