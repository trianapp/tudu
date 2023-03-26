package app.trian.tudu.feature.auth.resetPassword

import android.util.Patterns
import app.trian.tudu.R
import app.trian.tudu.base.BaseViewModel
import app.trian.tudu.data.domain.user.ChangePasswordUseCase
import app.trian.tudu.data.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ChangePasswordUseCase
) : BaseViewModel<ResetPasswordState, ResetPasswordEvent>(ResetPasswordState()) {
    init {
        handleActions()
    }

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = true) }

    private fun validateData(cb: suspend (String) -> Unit) = asyncWithState {
        when {
            email.isEmpty() -> showSnackbar(R.string.alert_email_empty)
            !Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches() -> showSnackbar(R.string.alert_validation_email)

            else -> cb(email)
        }
    }

    private fun handleResponse(result: Response<Boolean>) {
        when (result) {
            is Response.Error -> {
                hideLoading()
                showSnackbar(result.message)
            }
            Response.Loading -> showLoading()
            is Response.Result -> {
                hideLoading()
                showSnackbar(R.string.message_success_reset_password)
                navigateUp()
            }
        }
    }

    override fun handleActions() = onEvent { event ->
        when (event) {
            ResetPasswordEvent.Submit -> validateData { email ->
                resetPasswordUseCase(email).collect(::handleResponse)
            }
        }
    }
}