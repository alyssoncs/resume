package alysson.cirilo.resume.entities.test.databuilders

import alysson.cirilo.resume.entities.LinkedInformation
import alysson.cirilo.resume.entities.ProjectOrPublication
import java.net.URL

class ProjectOrPublicationBuilder {
    private var projectName: String = "cool project"
    private var url: URL = URL("https://www.github.com/alyssoncs/coolproject")
    private var description: String = "a very cool project"

    fun named(name: String) = builderMethod {
        projectName = name
    }

    fun hostedOn(urlSpec: String) = builderMethod {
        url = URL(urlSpec)
    }

    fun description(description: String) = builderMethod {
        this.description = description
    }

    fun build(): ProjectOrPublication {
        return ProjectOrPublication(
            title = LinkedInformation(
                displayName = projectName,
                url = url,
            ),
            description = description,
        )
    }
}

fun aProject() = ProjectOrPublicationBuilder()
fun aPublication() = aProject()
