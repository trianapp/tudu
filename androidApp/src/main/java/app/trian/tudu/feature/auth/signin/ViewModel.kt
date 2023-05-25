package app.trian.tudu.feature.auth.signin

import app.trian.tudu.R
import app.trian.tudu.base.BaseViewModel
import app.trian.tudu.data.domain.user.SignInWithEmailAndPasswordUseCase
import app.trian.tudu.data.domain.user.SignInWithGoogleUseCase
import app.trian.tudu.data.utils.Response
import app.trian.tudu.feature.dashboard.home.Home
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInWithEmailUseCase: SignInWithEmailAndPasswordUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase
) : BaseViewModel<SignInState, SignInEvent>(SignInState()) {
    init {
        handleActions()
    }


    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }

    private fun validateData(
        cb: suspend (String, String) -> Unit
    ) = asyncWithState {
        when {
            email.isEmpty() || password.isEmpty() ->
                showSnackbar(R.string.message_password_or_email_cannot_empty)

            else -> cb(email, password)
        }
    }

    private fun handleResponse(result: Response<FirebaseUser>) {
        when (result) {
            Response.Loading -> showLoading()
            is Response.Error -> {
                hideLoading()
                result.showErrorSnackbar()
            }

            is Response.Result -> {
                hideLoading()
                showSnackbar(R.string.text_message_welcome_user, result.data.displayName.orEmpty())
                navigateAndReplaceAll(Home.routeName)
            }
        }
    }

    override fun handleActions() = onEvent { event ->
        when (event) {
            SignInEvent.SignInWithEmail -> validateData { email, password ->
                signInWithEmailUseCase(email, password).collect(::handleResponse)
            }
            is SignInEvent.SignInWithGoogle -> signInWithGoogleUseCase(event.result).collect(::handleResponse)
        }
    }

}