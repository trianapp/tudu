package app.trian.tudu.feature.appSetting

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import app.trian.tudu.data.theme.ThemeData
import kotlinx.parcelize.Parcelize


@Parcelize
@Immutable
data class AppSettingState(
    val settingId: String = "",
    val listTaskType: String = "",
    val lastSync: String = "",
    val dateFormat: String = "",
    val timeFormat: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val showDialogTheme: Boolean = false,

    ) : Parcelable

sealed class AppSettingEvent {
    data class SelectedTheme(val theme: ThemeData) : AppSettingEvent()

}