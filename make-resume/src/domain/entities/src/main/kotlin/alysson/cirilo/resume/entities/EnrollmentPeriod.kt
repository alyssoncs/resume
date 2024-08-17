package alysson.cirilo.resume.entities

import java.time.YearMonth

data class EnrollmentPeriod(
    val start: YearMonth,
    val end: EndDate,
) {
    sealed interface EndDate {
        data class Past(val date: YearMonth) : EndDate {
            constructor(year: Int, month: Int) : this(YearMonth.of(year, month))
        }

        data object Present : EndDate
    }
}
