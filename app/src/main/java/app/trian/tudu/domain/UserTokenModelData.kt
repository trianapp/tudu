package app.trian.tudu.domain

import android.annotation.SuppressLint
import app.trian.tudu.common.fromOffsetDateTime
import java.time.OffsetDateTime
@SuppressLint("NewApi")
data class UserToken(
    var token:String="",
    var created_at:OffsetDateTime= OffsetDateTime.now(),
    val updated_at:OffsetDateTime= OffsetDateTime.now()
)


fun UserToken.toHashMap()= hashMapOf(
    "token" to token,
    "created_at" to created_at.fromOffsetDateTime(),
    "updated_at" to updated_at.fromOffsetDateTime()
)