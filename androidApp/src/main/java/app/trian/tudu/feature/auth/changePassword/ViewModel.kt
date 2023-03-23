package app.trian.tudu.feature.auth.changePassword

import app.trian.tudu.R
import app.trian.tudu.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
) : BaseViewModel<ChangePasswordState, ChangePasswordEvent>(ChangePasswordState()) {
    init {
        handleActions()
    }

    private fun submitChangePassword() = async {
        with(uiState.value) {
            when {
                newPassword != confirmPassword ->
                    showSnackbar(R.string.message_confirm_password_not_match)

                newPassword.isEmpty() || confirmPassword.isEmpty() ->
                    showSnackbar(R.string.message_change_password_field_empty)

                else -> Unit
            }
        }

    }

    override fun handleActions() = onEvent {
        when (it) {
            ChangePasswordEvent.Submit -> submitChangePassword()
        }
    }

}