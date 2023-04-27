package app.trian.tudu.data.model

import app.trian.tudu.base.extensions.Empty
import app.trian.tudu.data.theme.ThemeData
import app.trian.tudu.table.appSetting.AppSetting
import com.google.errorprone.annotations.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class AppSettingModel(
    @SerialName("settingId")
    val settingId: String = String.Empty,
    @SerialName("theme")
    val theme: ThemeData = ThemeData.DEFAULT,
    @SerialName("createdAt")
    val createdAt: String = String.Empty,
    @SerialName("updatedAt")
    val updatedAt: String = String.Empty
)

@Keep
fun AppSetting.toModel() = AppSettingModel(
    settingId = settingId,
    theme = ThemeData.valueOf(theme),
    createdAt = createdAt,
    updatedAt = updatedAt
)