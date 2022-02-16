package app.trian.datepicker

import org.joda.time.DateTime

/**
 * Utility
 * author Trian Damai
 * created_at 16/02/22 - 09.30
 * site https://trian.app
 */

fun DateTime.getMonth():String{

    return this.toString("MMM")
}

fun DateTime.getDayName():String{

    return this.toString("EE")
}