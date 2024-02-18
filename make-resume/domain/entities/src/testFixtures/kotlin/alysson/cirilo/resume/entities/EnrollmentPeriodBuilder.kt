package alysson.cirilo.resume.entities

import java.time.YearMonth

class EnrollmentPeriodBuilder private constructor(
    private val start: YearMonth,
    private val end: EnrollmentPeriod.EndDate,
) {
    constructor() : this(
        start = YearMonth.now(),
        end = EnrollmentPeriod.EndDate.Present,
    )

    fun from(month: Int, year: Int) = EnrollmentPeriodBuilder(YearMonth.of(year, month), end)

    fun to(month: Int, year: Int) = to(YearMonth.of(year, month))

    fun to(end: YearMonth) = EnrollmentPeriodBuilder(start, EnrollmentPeriod.EndDate.Past(end))

    fun upToNow() = EnrollmentPeriodBuilder(start, EnrollmentPeriod.EndDate.Present)

    fun build(): EnrollmentPeriod {
        return EnrollmentPeriod(start, end)
    }
}

fun period() = EnrollmentPeriodBuilder()
