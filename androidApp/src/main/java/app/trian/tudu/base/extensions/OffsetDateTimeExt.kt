package app.trian.tudu.base.extensions

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Locale

/**
 * Date Utils
 * author Trian Damai
 * created_at 30/01/22 - 21.32
 * site https://trian.app
 */



fun LocalDate?.toReadableDate(pattern:String="d MMMM, yyyy"):String{

    if(this == null) return "Date not valid"
    return this.formatDate(pattern.ifEmpty { "d MMMM, yyyy" })
}

fun LocalDate.getDateUntil(to:LocalDate):String{
    return "${this.formatDate("d MMM")} - ${to.formatDate("d MMM, yyyy")}"

}

fun getNowMillis(): LocalDate = LocalDate.now()


fun LocalDate.getPreviousDate() = this.minusDays(1)

fun LocalDate.getNextDate() = this.plusDays(1)

fun LocalDate.getDayofWeek() = this.minusDays(7)

fun LocalDate.getPreviousWeek() = this.minusDays(7)

fun LocalDate.getNextWeek() = this.plusDays(7)


fun LocalDate.formatDate(pattern: String):String{
    if(pattern.isBlank()){
        return this.format(DateTimeFormatter.BASIC_ISO_DATE)
    }
    return this.format(DateTimeFormatter.ofPattern(pattern))
}


fun String?.toOffsetDateTime():OffsetDateTime?{
    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    return this?.let{
        return formatter.parse(it,OffsetDateTime::from)
    }
}


fun OffsetDateTime?.fromOffsetDateTime():String?{
    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    return this?.format(formatter)
}



fun daysOfWeekFromLocale(): Array<DayOfWeek> {
    val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
    var daysOfWeek = DayOfWeek.values()
    // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
    // Only necessary if firstDayOfWeek != DayOfWeek.MONDAY which has ordinal 0.
    if (firstDayOfWeek != DayOfWeek.MONDAY) {
        val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
        val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
        daysOfWeek = rhs + lhs
    }
    return daysOfWeek
}