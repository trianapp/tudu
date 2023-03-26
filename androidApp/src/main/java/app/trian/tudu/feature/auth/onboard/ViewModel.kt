package app.trian.tudu.feature.auth.onboard

import app.trian.tudu.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor(
) : BaseViewModel<OnboardState, OnboardEvent>(OnboardState()) {
    init {
        handleActions()
    }

    override fun handleActions() = onEvent {}

}