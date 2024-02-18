package alysson.cirilo.resume.entities

import java.time.LocalDate

class EnrollmentPeriodBuilder private constructor(
    private val start: LocalDate,
    private val end: EnrollmentPeriod.EndDate,
) {
    constructor() : this(
        start = LocalDate.now(),
        end = EnrollmentPeriod.EndDate.Present,
    )

    fun from(month: Int, year: Int) = EnrollmentPeriodBuilder(LocalDate.of(year, month, 1), end)

    fun to(month: Int, year: Int) = to(LocalDate.of(year, month, 1))

    fun to(end: LocalDate) = EnrollmentPeriodBuilder(start, EnrollmentPeriod.EndDate.Past(end))

    fun upToNow() = EnrollmentPeriodBuilder(start, EnrollmentPeriod.EndDate.Present)

    fun build(): EnrollmentPeriod {
        return EnrollmentPeriod(start, end)
    }
}

fun period() = EnrollmentPeriodBuilder()
