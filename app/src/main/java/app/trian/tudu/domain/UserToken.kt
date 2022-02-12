package app.trian.tudu.domain

data class UserToken(
    var token:String="",
    var created_at:Long=0,
    val updated_at:Long=0
)


fun UserToken.toHashMap()= hashMapOf(
    "token" to token,
    "created_at" to created_at,
    "updated_at" to updated_at
)