package alysson.cirilo.resume.entities

import java.time.LocalDate

data class EnrollmentPeriod(
    val start: LocalDate,
    val end: EndDate,
) {
    sealed interface EndDate {
        data class Past(val date: LocalDate) : EndDate
        object Present : EndDate
    }
}
