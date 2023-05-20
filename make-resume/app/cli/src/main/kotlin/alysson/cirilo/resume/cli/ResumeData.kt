package alysson.cirilo.resume.cli

import alysson.cirilo.resume.entities.BulletPoint
import alysson.cirilo.resume.entities.ContactInformation
import alysson.cirilo.resume.entities.Degree
import alysson.cirilo.resume.entities.EnrollmentPeriod
import alysson.cirilo.resume.entities.JobExperience
import alysson.cirilo.resume.entities.LinkedInformation
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
        displayName = "Remote (UTC-3), Brazil",
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
                "Built smart object encounter notification system for an Android IoT middleware using ",
                "Java",
                ".",
            ),
            BulletPoint(
                "Programmed ",
                "Arduino",
                " boards using ",
                "C programming",
                " language to connect to Bluetooth Low Energy devices."
            ),
        )

        private val intern = Role(
            title = "Intern",
            start = LocalDate.of(2019, 5, 1),
            end = LocalDate.of(2019, 7, 1),
            BulletPoint("Developed an indoor location system."),
            BulletPoint(
                "Developed a system that manages the relationships between users, bluetooth beacons and physical spaces using ",
                "Spring Boot",
                ".",
            ),
            BulletPoint(
                "Developed Android application that detects bluetooth beacons and registers this encounter in a web service using ",
                "Retrofit",
                ".",
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
                "Built a flexible service and API for storing log information of other systems using Spring Boot and ",
                "MongoDB",
                ".",
            ),
            BulletPoint(
                "Built a ",
                "Clean Architecture",
                " Android app, using ",
                "Kotlin",
                ", ",
                "Coroutines",
                " and Firebase Cloud Messaging allowing visitors to schedule and be notified about virtual visits to their imprisoned family members, important resource during the Covid-19 pandemic.",
            ),
        )

        private val midLevel = Role(
            title = "Mid-level Developer",
            start = LocalDate.of(2020, 7, 1),
            end = LocalDate.of(2020, 9, 1),
            BulletPoint("Developed the Android app that allows searching information about the state's prisoners and fugitives using Kotlin."),
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
                "Built a digital wallet and credit card rewards app for a big bank using coroutines and ",
                "Koin",
                " as the dependency injection framework.",
            ),
            BulletPoint(
                "Integrated code coverage tool in a ",
                "multi-module",
                " Android project.",
            ),
            BulletPoint(
                "Implemented the subsystem responsible for the digital signature of all http requests made from the app with ",
                "OkHttp interceptors",
                ".",
            ),
            BulletPoint("Designed a testable biometric authentication abstraction adapting the original callback syntax to coroutines."),
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
                "Worked at the checkout team on a ",
                "single-activity",
                ", clean architecture, multi-module application, using ",
                "Dagger",
                ".",
            ),
            BulletPoint("Worked on a plugin based-architecture that enables loading different payment methods, delivery options and business logic."),
            BulletPoint("Created a script to manage code ownership making the process less error-prone given the frequency teams structure changes."),
        )

        private val androidDeveloper2 = Role(
            title = "Android Developer II",
            start = LocalDate.of(2022, 4, 1),
            end = LocalDate.of(2022, 7, 1),
            BulletPoint("Implemented dependency injection model for checkout plugins to solve inefficient manual injection."),
            BulletPoint("Implemented dynamic onboarding, allowing PMs to control the order and timing of onboarding based on remote configs and plugin availability. Improving user engagement and educating users on effective checkout feature usage."),
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
            BulletPoint("Worked on a geographically distributed team."),
            BulletPoint("Delivered features on the voice assistant in Outlook Android app."),
            BulletPoint(
                "Worked on the M365 copilot on Outlook mobile powered by GPT-4 using ",
                "Jetpack Compose",
                ".",
            ),
            BulletPoint("Developed a flexible dependency injection model for a library that integrates with host apps without exposing the framework."),
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
            url = URL("https://github.com/alyssoncs/cirilo-algorithm/"),
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
            url = URL("https://revistas.unifacs.br/index.php/rsc/article/view/6890"),
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

