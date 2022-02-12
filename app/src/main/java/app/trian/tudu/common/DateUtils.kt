package app.trian.tudu.common

import org.joda.time.DateTime

/**
 * Date Utils
 * author Trian Damai
 * created_at 30/01/22 - 21.32
 * site https://trian.app
 */

fun Long.toReadableDate():String{
    if(this in 0..1){
        return "No deadline"
    }
    val date = DateTime(this).toLocalDateTime()
    return date.toString("d MMMM, yyyy")
}

fun getNowMillis ()= DateTime.now().millis