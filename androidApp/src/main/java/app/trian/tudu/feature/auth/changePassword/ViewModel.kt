package app.trian.tudu.feature.auth.changePassword

import app.trian.tudu.R
import app.trian.tudu.base.BaseViewModel
import app.trian.tudu.data.domain.user.ChangePasswordUseCase
import app.trian.tudu.data.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase
) : BaseViewModel<ChangePasswordState, ChangePasswordEvent>(ChangePasswordState()) {
    init {
        handleActions()
    }

    private fun showLoading()  = commit { copy(isLoading = true) }
    private fun hideLoading()  = commit { copy(isLoading = false) }


    private fun validateData(
        cb: suspend (String) -> Unit
    ) = async {
        with(uiState.value) {
            when {
                newPassword != confirmPassword ->
                    showSnackbar(R.string.message_confirm_password_not_match)

                newPassword.isEmpty() || confirmPassword.isEmpty() ->
                    showSnackbar(R.string.message_change_password_field_empty)

                else -> cb(newPassword)
            }
        }

    }

    private fun handleResponse(result: Response<Boolean>) = async {
        when(result){
            is Response.Error -> {
                hideLoading()
            }
            Response.Loading -> showLoading()
            is Response.Result -> {
                hideLoading()
                showSnackbar(R.string.text_message_success_change_password)
            }
        }
    }

    override fun handleActions() = onEvent { event ->
        when (event) {
            ChangePasswordEvent.Submit -> validateData { newPassword ->
                changePasswordUseCase(newPassword).collect(::handleResponse)
            }
        }
    }

}