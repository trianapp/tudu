package app.trian.tudu.feature.auth.resetPassword

import android.util.Patterns
import app.trian.tudu.R
import app.trian.tudu.base.BaseViewModel
import app.trian.tudu.data.sdk.auth.AuthSDK
import app.trian.tudu.data.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authSDK: AuthSDK
) : BaseViewModel<ResetPasswordState, ResetPasswordEvent>(ResetPasswordState()) {
    init {
        handleActions()
    }

    private fun validateData(cb: suspend (String) -> Unit) = async {
        with(uiState.value) {
            when {
                email.isEmpty() -> showSnackbar(R.string.alert_email_empty)
                !Patterns.EMAIL_ADDRESS
                    .matcher(email)
                    .matches() -> showSnackbar(R.string.alert_validation_email)

                else -> cb(email)
            }
        }
    }

    private fun handleResponse(result: Response<Boolean>) {
        when (result) {
            is Response.Error -> showSnackbar(result.message)
            Response.Loading -> Unit
            is Response.Result -> {
                showSnackbar(R.string.message_success_reset_password)
                navigateUp()
            }
        }
    }

    override fun handleActions() = onEvent { event ->
        when (event) {
            ResetPasswordEvent.Submit -> validateData { email ->
                authSDK.resetPasswordByEmail(email)
                    .catch { }
                    .collect(::handleResponse)
            }
        }
    }
}