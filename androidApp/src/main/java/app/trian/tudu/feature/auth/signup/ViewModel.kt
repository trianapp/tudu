package app.trian.tudu.feature.auth.signup

import android.util.Patterns
import app.trian.tudu.R
import app.trian.tudu.base.BaseViewModel
import app.trian.tudu.data.domain.user.SignUpWithEmailAndPasswordUseCase
import app.trian.tudu.data.utils.Response
import app.trian.tudu.feature.auth.signin.SignIn
import app.trian.tudu.feature.auth.signup.SignUpEvent.SignUpWithEmail
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpWithEmailUseCase: SignUpWithEmailAndPasswordUseCase
) : BaseViewModel<SignUpState, SignUpEvent>(SignUpState()) {
    init {
        handleActions()
    }

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }
    private fun validateData(cb: suspend (String, String, String) -> Unit) = asyncWithState {
        when {
            email.isEmpty() ||
                    password.isEmpty() ||
                    displayName.isEmpty() -> showSnackbar(R.string.message_password_or_email_cannot_empty)

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> showSnackbar(R.string.alert_validation_email)
            else -> {
                cb(displayName, email, password)
            }
        }
    }

    private fun handleResponse(result: Response<FirebaseUser>) {
        when (result) {
            Response.Loading -> showLoading()
            is Response.Error -> {
                hideLoading()
                showSnackbar(result.message)
            }
            is Response.Result -> {
                hideLoading()
                showSnackbar(R.string.text_message_success_register)
                navigateAndReplaceAll(SignIn.routeName)
            }
        }
    }

    override fun handleActions() = onEvent {
        when (it) {
            SignUpWithEmail -> validateData { displayName, email, password ->
                signUpWithEmailUseCase(displayName = displayName, email = email, password = password)
                    .collect(::handleResponse)
            }

        }
    }

}