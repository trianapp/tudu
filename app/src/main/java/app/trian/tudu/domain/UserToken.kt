package app.trian.tudu.domain

data class UserToken(
    var token:String="",
    var created_at:Long=0,
    val updated_at:Long=0
)
