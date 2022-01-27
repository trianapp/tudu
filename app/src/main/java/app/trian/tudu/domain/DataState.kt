package app.trian.tudu.domain

sealed class DataState<out T>{
    object LOADING:DataState<Nothing>()
    data class onData<out Result>(val data:Result):DataState<Result>()
    data class onFailure(val message:String):DataState<Nothing>()
}
