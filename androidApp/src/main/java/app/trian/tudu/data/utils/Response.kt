/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package app.trian.tudu.data.utils

sealed class Response<out R>{
    object Loading: Response<Nothing>()
    data class Result<out Result>(val data:Result): Response<Result>()
    data class Error(val message:String="",val code:Int=0): Response<Nothing>()
}

fun <T> Response<T>.getValue(cb:(T)->Unit){
    when(this){
        is Response.Error -> Unit
        Response.Loading -> Unit
        is Response.Result -> {
            cb(this.data)
        }
    }
}
