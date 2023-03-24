package app.trian.tudu.feature.editProfile

import app.trian.tudu.R
import app.trian.tudu.base.BaseViewModel
import app.trian.tudu.data.sdk.auth.AuthSDK
import app.trian.tudu.data.utils.Response
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val authSDK: AuthSDK
) : BaseViewModel<EditProfileState, EditProfileEvent>(EditProfileState()) {
    init {
        handleActions()
        getProfileUser()
    }

    private fun showLoading() = commit { copy(isLoading = true) }
    private fun hideLoading() = commit { copy(isLoading = false) }
    private fun getProfileUser() = async {
        authSDK.getCurrentUser().collect(::handleProfileResponse)
    }

    private fun validateData(cb: suspend (String) -> Unit) = async {
        with(uiState.value) {
            when {
                displayName.isEmpty() -> showSnackbar(R.string.alert_email_empty)
                else -> cb(displayName)
            }
        }
    }

    private fun handleProfileResponse(result: Response<FirebaseUser?>) {
        when (result) {
            is Response.Error -> showSnackbar(result.message)
            Response.Loading -> Unit
            is Response.Result -> commit {
                copy(
                    displayName = result.data?.displayName.orEmpty(),
                )
            }
        }
    }

    private fun handleSubmitResponse(result: Response<FirebaseUser?>) {
        when (result) {
            is Response.Error -> {
                hideLoading()
                showSnackbar(result.message)
            }

            Response.Loading -> showLoading()
            is Response.Result -> {
                showSnackbar(R.string.text_message_success_change_profile)
                hideLoading()
            }
        }
    }

    override fun handleActions() = onEvent { event ->
        when (event) {
            EditProfileEvent.Submit -> validateData { displayName ->
                authSDK.updateProfile(displayName)
                    .collect(::handleSubmitResponse)
            }
        }
    }
}