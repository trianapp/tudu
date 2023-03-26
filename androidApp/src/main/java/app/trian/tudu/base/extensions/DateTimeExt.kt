package app.trian.tudu.base.extensions

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Locale

/**
 * Date Utils
 * author Trian Damai
 * created_at 30/01/22 - 21.32
 * site https://trian.app
 */


fun LocalDate?.toReadableDate(pattern: String = "d MMMM, yyyy"): String {

    if (this == null) return "Date not valid"
    return this.formatDate(pattern.ifEmpty { "d MMMM, yyyy" })
}

fun LocalDate.getHeaderChart(): String {
    return "${this.formatDate("d MMM")} - ${this.plusDays(6).formatDate("d MMM, yyyy")}"
}


fun getNowMillis(): LocalDate = LocalDate.now()

fun LocalDate.formatDate(pattern: String): String {
    if (pattern.isBlank()) {
        return this.format(DateTimeFormatter.BASIC_ISO_DATE)
    }
    return this.format(DateTimeFormatter.ofPattern(pattern))
}


fun LocalDate.getFirstDays(): Pair<LocalDate, LocalDate> {

    var first: LocalDate = this

    while (first.dayOfWeek != DayOfWeek.SUNDAY) {
        first = first.minusDays(1);
    }
    return Pair(
        first,
        first.plusDays(6)
    )

}

fun daysOfWeek() = DayOfWeek.values().rotateRight(7 - WeekFields.of(Locale.getDefault()).firstDayOfWeek.ordinal)


internal fun <T> Array<T>.rotateRight(n: Int): List<T> = takeLast(n) + dropLast(n)
