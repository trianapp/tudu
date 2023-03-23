package app.trian.tudu.feature.splash

import app.trian.tudu.base.BaseViewModel
import app.trian.tudu.data.sdk.auth.AuthSDK
import app.trian.tudu.feature.auth.onboard.Onboard
import app.trian.tudu.feature.dashboard.home.Home
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authSDK: AuthSDK
) : BaseViewModel<SplashUiState, SplashEvent>(SplashUiState()) {
    init {
        handleActions()
    }

    private fun checkIfUserLoggedIn() = async {
        if (authSDK.isLoggedIn()) {
            navigateAndReplaceAll(Home.routeName)
            onCleared()
        } else {
            navigateAndReplaceAll(Onboard.routeName)
            onCleared()
        }
    }

    override fun handleActions() = onEvent {
        when (it) {
            SplashEvent.CheckSession -> checkIfUserLoggedIn()
        }
    }

}