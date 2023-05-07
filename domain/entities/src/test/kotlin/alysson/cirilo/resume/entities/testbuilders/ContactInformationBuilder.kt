package alysson.cirilo.resume.entities.testbuilders

import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.LinkedInformation
import java.net.URL

class ContactInformationBuilder {
    fun build(): ContactInformation {
        return ContactInformation(
            email = LinkedInformation(
                displayName = "alysson.cirilo@gmail.com",
                url = URL("mailto:alysson.cirilo@gmail.com"),
            ),
            linkedin = LinkedInformation(
                displayName = "linkedin.com/in/alysson-cirilo",
                url = URL("https://www.linkedin.com/in/alysson-cirilo"),
            ),
            github = LinkedInformation(
                displayName = "github.com/alyssoncs",
                url = URL("https://www.github.com/alyssoncs")
            ),
            location = LinkedInformation(
                displayName = "Remote, Brazil",
                url = URL("https://www.example.com"),
            ),
        )
    }
}
