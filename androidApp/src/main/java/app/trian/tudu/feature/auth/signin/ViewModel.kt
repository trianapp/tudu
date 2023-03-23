package app.trian.tudu.feature.auth.signin

import app.trian.tudu.R
import app.trian.tudu.base.BaseViewModel
import app.trian.tudu.data.sdk.auth.AuthSDK
import app.trian.tudu.data.utils.Response
import app.trian.tudu.feature.dashboard.home.Home
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authSDK: AuthSDK
) : BaseViewModel<SignInState, SignInEvent>(SignInState()) {
    init {
        handleActions()
    }

    private fun signInWithEmail(
        cb: suspend (String, String) -> Unit
    ) = async {
        with(uiState.value) {
            when {
                email.isEmpty() || password.isEmpty() -> {
                    showSnackbar(R.string.message_password_or_email_cannot_empty)
                }

                password.length < 8 ->
                    showSnackbar(R.string.message_password_less_than_8)

                else -> cb(email, password)
            }
        }
    }

    private fun handleResponse(result: Response<FirebaseUser?>) {
        when (result) {
            is Response.Error -> showSnackbar(result.message)
            Response.Loading -> Unit
            is Response.Result -> {
                showSnackbar("Selamat datang ${result.data?.displayName}")
                navigateAndReplaceAll(Home.routeName)
            }
        }
    }

    private fun signInWithGoogle(result: Task<GoogleSignInAccount>?) = async {
        if (result == null) {
            showSnackbar("Cancel by provider")
        } else {
            val task = result.await()
            authSDK.signInWithGoogle(
                idToken = task.idToken,
            ).catch { showSnackbar(it.message.orEmpty()) }
                .collect(::handleResponse)
        }
    }

    override fun handleActions() = onEvent { event ->
        when (event) {
            SignInEvent.SignInWithEmail -> signInWithEmail { email, password ->
                authSDK
                    .signInWithEmail(email, password)
                    .catch {
                        showSnackbar(it.message.orEmpty())
                    }
                    .collect(::handleResponse)
            }

            is SignInEvent.SignInWithGoogle -> signInWithGoogle(event.result)
        }
    }

}