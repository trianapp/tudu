package app.trian.tudu.feature.appSetting

import app.trian.tudu.base.BaseViewModel
import app.trian.tudu.feature.appSetting.AppSettingEvent.SetDateFormat
import app.trian.tudu.feature.appSetting.AppSettingEvent.SetTimeFormat
import app.trian.tudu.feature.appSetting.AppSettingEvent.ShowDateFormat
import app.trian.tudu.feature.appSetting.AppSettingEvent.ShowTimeFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppSettingViewModel @Inject constructor(
) : BaseViewModel<AppSettingState, AppSettingEvent>(AppSettingState()) {
    init {
        handleActions()
    }

    override fun handleActions() = onEvent {
        when (it) {
            is SetDateFormat -> commit { copy(dateFormat = it.format) }
            is SetTimeFormat -> commit { copy(timeFormat = it.format) }
            is ShowDateFormat -> commit { copy(showDialogDateFormat = it.isShow) }
            is ShowTimeFormat -> commit { copy(showDialogTimeFormat = it.isShow) }
        }
    }

}