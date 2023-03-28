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
    data class Error(val message:String="",val code:Int=0,val stringId:Int=0): Response<Nothing>()
}


sealed class ResponseWithProgress<out R>{
    object Loading: ResponseWithProgress<Nothing>()
    data class Finish<out Result>(val data:Result): ResponseWithProgress<Result>()
    data class Progress(val progress:Int): ResponseWithProgress<Nothing>()
    data class Error(val message:String="",val code:Int=0): ResponseWithProgress<Nothing>()
}

