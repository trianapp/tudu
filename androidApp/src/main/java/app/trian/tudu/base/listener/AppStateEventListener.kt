/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package app.trian.tudu.base.listener

fun interface AppStateEventListener {
    fun onEvent(eventName:String)
}