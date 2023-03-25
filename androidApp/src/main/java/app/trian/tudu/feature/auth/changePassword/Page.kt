package app.trian.tudu.feature.auth.changePassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.trian.tudu.R
import app.trian.tudu.ApplicationState
import app.trian.tudu.base.BaseMainApp
import app.trian.tudu.base.UIWrapper
import app.trian.tudu.components.AppbarBasic
import app.trian.tudu.components.ButtonPrimary
import app.trian.tudu.components.DialogLoading
import app.trian.tudu.components.FormInput

object ChangePassword {
    const val routeName = "ChangePassword"
}

fun NavGraphBuilder.routeChangePassword(
    state: ApplicationState,
) {
    composable(ChangePassword.routeName) {
        ScreenChangePassword(appState = state)
    }
}

@Composable
internal fun ScreenChangePassword(
    appState: ApplicationState,
) = UIWrapper<ChangePasswordViewModel>(appState = appState) {
    val state by uiState.collectAsState()

    with(appState) {
        hideBottomAppBar()
        hideBottomSheet()
        setupTopAppBar {
            AppbarBasic(
                title = stringResource(id = R.string.title_appbar_change_password),
                onBackPressed = {
                    navigateUp()
                }
            )
        }
    }
    DialogLoading(
        show = state.isLoading,
        message = "Updating your password",
        title = "Please wait"
    )
    Column(
        modifier = Modifier
            .padding(
                horizontal = 16.dp
            )
    ) {
        FormInput(
            label = {
                Text(text = stringResource(R.string.label_input_new_password))
            },
            placeholder = stringResource(R.string.placeholder_input_new_password),
            showPasswordObsecure = true,
            initialValue = state.newPassword,
            onChange = {
                commit { copy(newPassword = it) }
            },
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(16.dp))
        FormInput(
            label = {
                Text(text = stringResource(R.string.label_input_confirm_new_password))
            },
            placeholder = stringResource(R.string.placeholder_input_confirm_new_password),
            showPasswordObsecure = true,
            initialValue = state.confirmPassword,
            onChange = {
                commit { copy(confirmPassword = it) }
            },
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(30.dp))
        ButtonPrimary(
            text = stringResource(R.string.btn_change_password)
        ) {
            dispatch(ChangePasswordEvent.Submit)
        }
    }


}


@Preview
@Composable
fun PreviewScreenChangePassword() {
    BaseMainApp(
        topAppBar = {
            AppbarBasic(title = stringResource(id = R.string.title_appbar_change_password))
        }
    ) {
        ScreenChangePassword(it)
    }
}