package app.trian.tudu.domain

/**
 * State of request data
 * author Trian Damai
 * created_at 29/01/22 - 12.47
 * site https://trian.app
 */
sealed class DataState<out T>{
    object LOADING:DataState<Nothing>()
    data class onData<out Result>(val data:Result):DataState<Result>()
    data class onFailure(val message:String):DataState<Nothing>()
}
