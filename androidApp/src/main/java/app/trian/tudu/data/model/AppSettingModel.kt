package app.trian.tudu.data.model

import app.trian.tudu.data.dateTime.DateFormat
import app.trian.tudu.data.dateTime.TimeFormat
import app.trian.tudu.data.theme.ThemeData
import appSetting.AppSetting

data class AppSettingModel(
    val settingId: String = "",
    val theme: ThemeData = ThemeData.DEFAULT,
    val lastSync: String = "",
    val dateFormat: DateFormat = DateFormat.DEFAULT,
    val timeFormat: TimeFormat = TimeFormat.TWENTY,
    val createdAt: String = "",
    val updatedAt:String=""
)


fun AppSettingModel.toEntity()= AppSetting(
    settingId = settingId,
    theme = theme.value,
    lastSync = lastSync,
    dateFormat = dateFormat.value,
    timeFormat = timeFormat.value,
    createdAt = createdAt,
    updatedAt = updatedAt
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