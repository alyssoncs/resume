package alysson.cirilo.resume.entities

class ProjectOrPublicationBuilder private constructor(
    private val title: LinkedInformationBuilder,
    private val description: String,
) {
    constructor() : this(
        title = aLinkedInfo()
            .displaying("cool project")
            .linkingTo("https://www.github.com/alyssoncs/coolproject"),
        description = "a very cool project",
    )

    fun named(name: String) = ProjectOrPublicationBuilder(title.displaying(name), description)

    fun hostedOn(urlSpec: String) =
        ProjectOrPublicationBuilder(title.linkingTo(urlSpec), description)

    fun description(description: String) = ProjectOrPublicationBuilder(title, description)

    fun build(): ProjectOrPublication {
        return ProjectOrPublication(
            title = title.build(),
            description = description,
        )
    }
}

fun aProject() = ProjectOrPublicationBuilder()
fun aPublication() = aProject()
