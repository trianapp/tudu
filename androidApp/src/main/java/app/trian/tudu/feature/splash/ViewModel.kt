package app.trian.tudu.feature.splash

import androidx.compose.ui.text.toUpperCase
import app.trian.tudu.base.BaseViewModel
import app.trian.tudu.data.domain.category.InsertCategoryOnFirstRunUseCase
import app.trian.tudu.data.domain.user.ChangeThemeUseCase
import app.trian.tudu.data.domain.user.CheckSessionUserUseCase
import app.trian.tudu.data.domain.user.GetUserSettingUseCase
import app.trian.tudu.data.theme.ThemeData
import app.trian.tudu.data.utils.Response
import app.trian.tudu.feature.auth.onboard.Onboard
import app.trian.tudu.feature.dashboard.home.Home
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkSessionUserUseCase: CheckSessionUserUseCase,
    private val insertCategoryOnFirstRunUseCase: InsertCategoryOnFirstRunUseCase,
    private val getUserSettingUseCase: GetUserSettingUseCase,
    private val changeThemeUseCase: ChangeThemeUseCase
) : BaseViewModel<SplashUiState, SplashEvent>(SplashUiState()) {
    init {
        handleActions()
    }

    private fun checkIfUserLoggedIn() = async {
        insertCategoryOnFirstRunUseCase().collect {}
        getSetting()
        if (checkSessionUserUseCase()) {
            navigateAndReplaceAll(Home.routeName)
            onCleared()
        } else {
            navigateAndReplaceAll(Onboard.routeName)
            onCleared()
        }
    }

    private fun getSetting() = async {
        getUserSettingUseCase().collect {
            when (it) {
                is Response.Result -> {
                    val theme = ThemeData.valueOf(it.data.theme.uppercase())
                    setTheme(theme)
                }

                else -> changeThemeUseCase(ThemeData.DEFAULT).collect()
            }
        }
    }

    override fun handleActions() = onEvent {
        when (it) {
            SplashEvent.CheckSession -> checkIfUserLoggedIn()
        }
    }

}