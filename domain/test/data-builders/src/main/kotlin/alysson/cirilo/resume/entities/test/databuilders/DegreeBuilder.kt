package alysson.cirilo.resume.entities.test.databuilders

import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.EnrollmentPeriod
import alysson.cirilo.resume.entities.LinkedInformation
import java.net.URL
import java.time.LocalDate

class DegreeBuilder {
    fun build(): Degree {
        return Degree(
            institution = LinkedInformation(
                displayName = "Top institution",
                url = URL("https://www.example.com"),
            ),
            location = "Brazil",
            degree = "BSc. in Computer Science",
            period = EnrollmentPeriod(
                start = LocalDate.now().minusDays(1),
                end = EnrollmentPeriod.EndDate.Past(LocalDate.now()),
            ),
        )
    }
}
