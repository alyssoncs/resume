package alysson.cirilo.resume.entities.test.databuilders

import alysson.cirilo.resume.entities.EnrollmentPeriod
import java.time.LocalDate

class EnrollmentPeriodBuilder {
    private var start: LocalDate = LocalDate.now()
    private var end: EnrollmentPeriod.EndDate = EnrollmentPeriod.EndDate.Present

    fun from(month: Int, year: Int) = builderMethod {
        this.start = LocalDate.of(year, month, 1)
    }

    fun to(month: Int, year: Int) = to(LocalDate.of(year, month, 1))

    fun to(end: LocalDate) = builderMethod {
        this.end = EnrollmentPeriod.EndDate.Past(end)
    }

    fun upToNow() = builderMethod {
        this.end = EnrollmentPeriod.EndDate.Present
    }

    fun build(): EnrollmentPeriod {
        return EnrollmentPeriod(start, end)
    }
}

fun period() = EnrollmentPeriodBuilder()
