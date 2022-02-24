package app.trian.tudu.common

import android.annotation.SuppressLint
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.temporal.WeekFields
import java.util.*

/**
 * Date Utils
 * author Trian Damai
 * created_at 30/01/22 - 21.32
 * site https://trian.app
 */

fun Long.toReadableDate(pattern:String=""):String{
    if(this in 0..1){
        return "No deadline"
    }
    val date = DateTime(this).toLocalDateTime()
    if(pattern.isBlank()) return date.toString("d MMMM, yyyy")
    return date.toString(pattern)
}

fun getNowMillis()= DateTime.now().millis

fun Long.getPreviousDate() = DateTime(this).minusDays(1).millis
fun Long.getNextDate() = DateTime(this).plusDays(1).millis
fun Long.getDayOfWeek() = DateTime(this).minusDays(7).millis
fun Long.getPreviousWeek() = DateTime(this).minusDays(7).millis
fun Long.getNextWeek() = DateTime(this).plusDays(7).millis


//https://stackoverflow.com/questions/14053079/simpledateformat-returns-24-hour-date-how-to-get-12-hour-date
@SuppressLint("SimpleDateFormat")
fun Long.formatDate(pattern : String = ""):String{
    if (pattern.isBlank()) {
        return SimpleDateFormat("dd/MM").format(this)
    }
    return SimpleDateFormat(pattern).format(this)

}

fun Long.getDateUntil(to:Long):String{

    val dateFrom = DateTime(this).toLocalDateTime()
    val dateTo = DateTime(to).toLocalDateTime()
   return "${dateFrom.toString("d MMM")} - ${dateTo.toString("d MMM, yyyy")}"

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