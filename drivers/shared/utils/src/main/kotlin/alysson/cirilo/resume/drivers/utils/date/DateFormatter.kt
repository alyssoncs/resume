package alysson.cirilo.resume.drivers.utils.date

import java.time.format.DateTimeFormatter
import java.util.Locale

private val locale = Locale.US
val workDateFormatter = DateTimeFormatter.ofPattern("MMM. yyyy").withLocale(locale)
val educationDateFormatter = DateTimeFormatter.ofPattern("yyyy").withLocale(locale)
