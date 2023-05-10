package alysson.cirilo.resume.entities.test.databuilders

import alysson.cirilo.resume.entities.LinkedInformation
import alysson.cirilo.resume.entities.ProjectOrPublication
import java.net.URL

class ProjectOrPublicationBuilder {
    fun build(): ProjectOrPublication {
        return ProjectOrPublication(
            title = LinkedInformation(
                displayName = "cool project",
                url = URL("https://www.github.com/alyssoncs/coolproject"),
            ),
            description = "a very cool project",
        )
    }
}
