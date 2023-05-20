package alysson.cirilo.resume.entities.test.databuilders

import alysson.cirilo.resume.entities.LinkedInformation
import alysson.cirilo.resume.entities.ProjectOrPublication
import java.net.URL

class ProjectOrPublicationBuilder private constructor(
    private val projectName: String,
    private val url: URL,
    private val description: String,
) {
    constructor() : this(
        projectName = "cool project",
        url = URL("https://www.github.com/alyssoncs/coolproject"),
        description = "a very cool project",
    )

    fun named(name: String) = ProjectOrPublicationBuilder(name, url, description)

    fun hostedOn(urlSpec: String) =
        ProjectOrPublicationBuilder(projectName, URL(urlSpec), description)

    fun description(description: String) =
        ProjectOrPublicationBuilder(projectName, url, description)

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
