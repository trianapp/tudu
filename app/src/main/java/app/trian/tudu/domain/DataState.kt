package app.trian.tudu.domain

/**
 * State of request data
 * author Trian Damai
 * created_at 29/01/22 - 12.47
 * site https://trian.app
 */
sealed class DataState<out T>{
    object OnLoading:DataState<Nothing>()
    data class OnData<out Result>(val data:Result):DataState<Result>()
    data class OnFailure(val message:String):DataState<Nothing>()
}
