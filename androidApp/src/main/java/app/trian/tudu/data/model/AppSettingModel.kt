package app.trian.tudu.data.model

import app.trian.tudu.data.dateTime.DateFormat
import app.trian.tudu.data.dateTime.TimeFormat
import app.trian.tudu.data.theme.ThemeData
import app.trian.tudu.table.appSetting.AppSetting
import com.google.errorprone.annotations.Keep

@Keep
data class AppSettingModel(
    val settingId: String = "",
    val theme: ThemeData = ThemeData.DEFAULT,
    val lastSync: String = "",
    val dateFormat: DateFormat = DateFormat.DEFAULT,
    val timeFormat: TimeFormat = TimeFormat.TWENTY,
    val createdAt: String = "",
    val updatedAt:String=""
)

fun AppSetting.toModel()= AppSettingModel(
    settingId = settingId,
    theme = ThemeData.valueOf(theme),
    lastSync = lastSync,
    dateFormat =  DateFormat.valueOf(dateFormat.orEmpty().ifEmpty { DateFormat.DEFAULT.value }),
    timeFormat = TimeFormat.valueOf(timeFormat.orEmpty().ifEmpty { TimeFormat.DEFAULT.value }),
    createdAt=createdAt,
    updatedAt=updatedAt
)