package app.trian.tudu.feature.appSetting

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize


@Parcelize
@Immutable
data class AppSettingState(
    var settingId: String = "",
    var listTaskType: String = "",
    var lastSync: String = "",
    var dateFormat: String = "",
    var timeFormat: String = "",
    var createdAt: String = "",
    var updatedAt: String = "",
    var showDialogDateFormat:Boolean=false,
    var showDialogTimeFormat:Boolean=false,

) : Parcelable

sealed class AppSettingEvent{
    class ShowDateFormat(val isShow:Boolean): AppSettingEvent()
    class ShowTimeFormat(val isShow:Boolean): AppSettingEvent()

    class SetDateFormat(val format:String): AppSettingEvent()
    class SetTimeFormat(val format: String): AppSettingEvent()

}