package alysson.cirilo.resume.serialization.models

import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

interface SerializableEnrollmentPeriodBuilder {
    fun from(month: Int, year: Int): SerializableEnrollmentPeriodBuilder
    fun from(start: String): SerializableEnrollmentPeriodBuilder
    fun upToNow(): SerializableEnrollmentPeriodBuilder
    fun to(month: Int, year: Int): SerializableEnrollmentPeriodBuilder
    fun to(end: String): SerializableEnrollmentPeriodBuilder
    fun build(): SerializableEnrollmentPeriod
}

private data class SerializableEnrollmentPeriodBuilderImpl(
    private val start: String = dateFormatter.format(YearMonth.now().minusMonths(1)),
    private val end: String = SerializableEnrollmentPeriod.CURRENT,
) : SerializableEnrollmentPeriodBuilder {

    override fun from(month: Int, year: Int) = from(dateFormatter.format(year, month))

    override fun from(start: String) = copy(start = start)

    override fun upToNow(): SerializableEnrollmentPeriodBuilder =
        to(SerializableEnrollmentPeriod.CURRENT)

    override fun to(month: Int, year: Int) = to(dateFormatter.format(year, month))

    override fun to(end: String) = copy(end = end)

    override fun build(): SerializableEnrollmentPeriod {
        return SerializableEnrollmentPeriod(from = start, to = end)
    }
}

fun periodDto(): SerializableEnrollmentPeriodBuilder {
    return SerializableEnrollmentPeriodBuilderImpl()
}

private val locale = Locale.US
private val dateFormatter = DateTimeFormatter.ofPattern("MM-yyyy").withLocale(locale)
private fun DateTimeFormatter.format(year: Int, month: Int): String {
    return format(YearMonth.of(year, month))
}
