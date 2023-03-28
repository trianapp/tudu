package app.trian.tudu.feature.appSetting

import app.trian.tudu.base.BaseViewModel
import app.trian.tudu.data.domain.user.ChangeThemeUseCase
import app.trian.tudu.data.theme.ThemeData
import app.trian.tudu.data.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class AppSettingViewModel @Inject constructor(
    private val changeThemeUseCase: ChangeThemeUseCase
) : BaseViewModel<AppSettingState, AppSettingEvent>(AppSettingState()) {
    init {
        handleActions()
    }

    private fun updateTheme(theme: ThemeData) = async {
        commit { copy(showDialogTheme = false) }
        setTheme(theme)
        changeThemeUseCase(theme).collect{}

    }

    override fun handleActions() = onEvent {
        when (it) {
            is AppSettingEvent.SelectedTheme -> updateTheme(it.theme)
        }
    }

}