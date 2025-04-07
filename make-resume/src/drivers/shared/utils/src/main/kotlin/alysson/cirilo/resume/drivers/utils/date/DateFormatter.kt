package alysson.cirilo.resume.drivers.utils.date

import java.time.format.DateTimeFormatter
import java.util.Locale

val workDateFormatter: DateTimeFormatter = formatter("MMM. yyyy")
val educationDateFormatter: DateTimeFormatter = formatter("yyyy")

internal fun formatter(pattern: String): DateTimeFormatter = DateTimeFormatter.ofPattern(pattern).withLocale(Locale.US)
