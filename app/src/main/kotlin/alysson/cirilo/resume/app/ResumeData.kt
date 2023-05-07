package alysson.cirilo.resume.app

import alysson.cirilo.resume.entities.BulletPoint
import alysson.cirilo.resume.entities.BulletPointContent
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.EnrollmentPeriod
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.LinkedInformation
import alysson.cirilo.resume.entities.ProfessionalSkill
import alysson.cirilo.resume.entities.ProjectOrPublication
import alysson.cirilo.resume.entities.Resume
import alysson.cirilo.resume.entities.Role
import java.net.URL
import java.time.LocalDate


fun makeResume(): Resume {
    return Resume(
        name = "Alysson Cirilo Silva",
        headline = listOf("Software Engineer", "Android Developer"),
        contactInformation = contactInformation,
        jobExperiences = jobExperiences,
        projectsAndPublications = projectOrPublications,
        education = degrees,
    )
}

private val contactInformation = ContactInformation(
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
        url = URL("https://www.google.com/maps?q=+Maranhão,+Brazil"),
    ),
)

private object Employments {

    object Lsdi {
        private val researchFellow = Role(
            title = "Undergraduate Research Fellow",
            start = LocalDate.of(2017, 4, 1),
            end = LocalDate.of(2019, 7, 1),
            BulletPoint(
                BulletPointContent.PlainText("Implemented a notification mechanism for discovery, connection and disconnection of smart objects in an IoT middleware using "),
                BulletPointContent.Skill(ProfessionalSkill("Java")),
                BulletPointContent.PlainText(" on the Android platform."),
            ),
            BulletPoint(
                BulletPointContent.PlainText("Developed embedded software using the "),
                BulletPointContent.Skill(ProfessionalSkill("C programming")),
                BulletPointContent.PlainText(" language on "),
                BulletPointContent.Skill(ProfessionalSkill("Arduino")),
                BulletPointContent.PlainText(" boards, including the use of Bluetooth Low Energy technology."),
            ),
        )

        private val intern = Role(
            title = "Intern",
            start = LocalDate.of(2019, 5, 1),
            end = LocalDate.of(2019, 7, 1),
            BulletPoint(BulletPointContent.PlainText("Developed an indoor location system.")),
            BulletPoint(
                BulletPointContent.PlainText("Developed a system that manages the relationships between users, bluetooth beacons and physical spaces using "),
                BulletPointContent.Skill(ProfessionalSkill("Spring Boot")),
                BulletPointContent.PlainText("."),
            ),
            BulletPoint(
                BulletPointContent.PlainText("Developed Android application that detects bluetooth beacons and registers this encounter in a web service using "),
                BulletPointContent.Skill(ProfessionalSkill("Retrofit")),
                BulletPointContent.PlainText("."),
            ),
        )

        val experience = JobExperience(
            company = LinkedInformation(
                displayName = "Laboratory of Intelligent Distributed Systems (LSDi)",
                url = URL("http://www.lsdi.ufma.br/projetos/"),
            ),
            location = "São Luís - MA, Brazil",
            roles = listOf(
                researchFellow,
                intern,
            ),
        )
    }

    object Seap {
        private val entryLevel = Role(
            title = "Entry-level Developer",
            start = LocalDate.of(2019, 9, 1),
            end = LocalDate.of(2020, 7, 1),
            BulletPoint(
                BulletPointContent.PlainText("Built a flexible service and API for storing log information of other systems using Spring Boot and "),
                BulletPointContent.Skill(ProfessionalSkill("MongoDB")),
                BulletPointContent.PlainText("."),
            ),
            BulletPoint(
                BulletPointContent.PlainText("Implemented a "),
                BulletPointContent.Skill(ProfessionalSkill("Clean Architecture")),
                BulletPointContent.PlainText(" app, using native Android with "),
                BulletPointContent.Skill(ProfessionalSkill("Kotlin")),
                BulletPointContent.PlainText(", "),
                BulletPointContent.Skill(ProfessionalSkill("Coroutines")),
                BulletPointContent.PlainText(" and Firebase Cloud Messaging, this app allowed visitors to schedule virtual visits to their imprisoned family members, important resource during the Covid-19 pandemic."),
            ),
            BulletPoint(BulletPointContent.PlainText("Collaborated with a team responsible for the system that manages all state's prison information.")),
        )

        private val midLevel = Role(
            title = "Mid-level Developer",
            start = LocalDate.of(2020, 7, 1),
            end = LocalDate.of(2020, 9, 1),
            BulletPoint(BulletPointContent.PlainText("Developed the Android app that allows searching information about the state's prisoners and fugitives using Kotlin.")),
        )

        val experience = JobExperience(
            company = LinkedInformation(
                displayName = "State Secretariat for Penitentiary Administration of Maranhão (SEAP-MA)",
                url = URL("http://seap.ma.gov.br/"),
            ),
            location = "São Luís - MA, Brazil",
            roles = listOf(
                entryLevel,
                midLevel,
            ),
        )
    }

    object Meta {
        private val androidDeveloper = Role(
            title = "Android Developer",
            start = LocalDate.of(2020, 9, 1),
            end = LocalDate.of(2021, 5, 1),
            BulletPoint(
                BulletPointContent.PlainText("Built a digital wallet and credit card rewards app for a big bank using coroutines and "),
                BulletPointContent.Skill(ProfessionalSkill("Koin")),
                BulletPointContent.PlainText(" as the dependency injection framework."),
            ),
            BulletPoint(
                BulletPointContent.PlainText("Integrated code coverage tool in a "),
                BulletPointContent.Skill(ProfessionalSkill("multi-module")),
                BulletPointContent.PlainText(" Android project."),
            ),
            BulletPoint(
                BulletPointContent.PlainText("Implemented the subsystem responsible for the digital signature of all http requests made from the app with "),
                BulletPointContent.Skill(ProfessionalSkill("OkHttp interceptors")),
                BulletPointContent.PlainText("."),
            ),
            BulletPoint(BulletPointContent.PlainText("Designed a testable biometric authentication abstraction adapting the original callback syntax to coroutines.")),
        )

        val experience = JobExperience(
            company = LinkedInformation(
                displayName = "Meta (Consulting Company)",
                url = URL("https://www.meta.com.br/"),
            ),
            location = "Remote, Brazil",
            roles = listOf(
                androidDeveloper,
            ),
        )
    }

    object IFood {
        private val androidDeveloper1 = Role(
            title = "Android Developer I",
            start = LocalDate.of(2021, 5, 1),
            end = LocalDate.of(2022, 4, 1),
            BulletPoint(
                BulletPointContent.PlainText("Worked at the checkout team on a "),
                BulletPointContent.Skill(ProfessionalSkill("single-activity")),
                BulletPointContent.PlainText(", clean architecture, multi-module application, using "),
                BulletPointContent.Skill(ProfessionalSkill("Dagger")),
                BulletPointContent.PlainText(""),
            ),
            BulletPoint(BulletPointContent.PlainText("Worked on a plugin based-architecture that enables the loading different payment methods, delivery options and other business logic.")),
            BulletPoint(BulletPointContent.PlainText("Created a script to manage code owners, making it easier to add/remove people and assign ownership to squads, with less errors and adaptability to team changes.")),
        )

        private val androidDeveloper2 = Role(
            title = "Android Developer II",
            start = LocalDate.of(2022, 4, 1),
            end = LocalDate.of(2021, 7, 1),
            BulletPoint(BulletPointContent.PlainText("Designed and implemented a dependency injection model for the checkout plugins, this solved the previous technical issue of manually injecting the plugins, which was cumbersome and inefficient.")),
            BulletPoint(BulletPointContent.PlainText("Implemented dynamic onboarding, allowing PMs to control the order and timing of onboarding based on remote configs and plugin availability. Improving user engagement and educating users on effective checkout feature usage.")),
        )

        val experience = JobExperience(
            company = LinkedInformation(
                displayName = "iFood",
                url = URL("https://www.ifood.com.br/"),
            ),
            location = "Remote, Brazil",
            roles = listOf(
                androidDeveloper1,
                androidDeveloper2,
            ),
        )
    }

    object Microsoft {
        private val swe2 = Role(
            title = "Android Software Engineer",
            start = LocalDate.of(2022, 7, 1),
            BulletPoint(BulletPointContent.PlainText("Worked on a geographically distributed team.")),
            BulletPoint(BulletPointContent.PlainText("Delivered features on the voice assistant in Outlook Android app.")),
            BulletPoint(
                BulletPointContent.PlainText("Worked on the M365 copilot on Outlook mobile powered by GPT-4 using "),
                BulletPointContent.Skill(ProfessionalSkill("Jetpack Compose")),
                BulletPointContent.PlainText("."),
            ),
            BulletPoint(BulletPointContent.PlainText("Designed and implemented a dependency injection model for a library that allows for consuming different capabilities from the host app without exposing the dependency injection framework.")),
        )

        val experience = JobExperience(
            company = LinkedInformation(
                displayName = "Microsoft",
                url = URL("https://www.microsoft.com/"),
            ),
            location = "Remote, Brazil",
            roles = listOf(
                swe2,
            ),
        )
    }
}

private val jobExperiences = listOf(
    Employments.Lsdi.experience,
    Employments.Seap.experience,
    Employments.Meta.experience,
    Employments.IFood.experience,
    Employments.Microsoft.experience,
)

private val projectOrPublications = listOf(
    ProjectOrPublication(
        title = LinkedInformation(
            displayName = "Cirilo's Algorithm",
            url = URL("https://github.com/alyssoncs/cirilo-algorithm/")
        ),
        description = "An algorithm for finding the smallest pair of values between two unsorted arrays.",
    ),
    ProjectOrPublication(
        title = LinkedInformation(
            displayName = "Jetpack Compose – Don't throw your presenters off",
            url = URL("https://dev.to/alyssoncs/jetpack-compose-don-t-throw-your-presenters-off-43fk")
        ),
        description = "Blog post about using model-view-presenter (MVP) on Jetpack Compose.",
    ),
    ProjectOrPublication(
        title = LinkedInformation(
            displayName = "Internet of Things Applied to Presence and Meeting Management of People in Smart Buildings",
            url = URL("https://revistas.unifacs.br/index.php/rsc/article/view/6890")
        ),
        description = "Paper on mobile computing and IoT.",
    ),
)

private val degrees = listOf(
    Degree(
        institution = LinkedInformation(
            displayName = "Federal University of Maranhão (UFMA)",
            url = URL("https://portalpadrao.ufma.br/site/"),
        ),
        location = "São Luís - MA, Brazil",
        degree = "BSc. in Computer Science",
        period = EnrollmentPeriod(
            start = LocalDate.of(2013, 1, 1),
            end = EnrollmentPeriod.EndDate.Past(LocalDate.of(2019, 1, 1)),
        ),
    ),
)

