package app.trian.tudu.data.model

import app.trian.tudu.data.theme.ThemeData
import app.trian.tudu.table.appSetting.AppSetting
import com.google.errorprone.annotations.Keep

@Keep
data class AppSettingModel(
    val settingId: String = "",
    val theme: ThemeData = ThemeData.DEFAULT,
    val createdAt: String = "",
    val updatedAt:String=""
)

fun AppSetting.toModel()= AppSettingModel(
    settingId = settingId,
    theme = ThemeData.valueOf(theme),
    createdAt=createdAt,
    updatedAt=updatedAt
)