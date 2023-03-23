package app.trian.tudu.feature.auth.resetPassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import app.trian.tudu.base.extensions.navigateUp
import app.trian.tudu.components.AppbarBasic
import app.trian.tudu.components.ButtonPrimary
import app.trian.tudu.components.FormInput

object ResetPassword {
    const val routeName = "ResetPassword"
}

fun NavGraphBuilder.routeResetPassword(
    state: ApplicationState,
) {
    composable(ResetPassword.routeName) {
        ScreenResetPassword(appState = state)
    }
}

@Composable
internal fun ScreenResetPassword(
    appState: ApplicationState,
) = UIWrapper<ResetPasswordViewModel>(appState = appState) {
        val state by uiState.collectAsState()
        with(appState) {
            hideBottomAppBar()
            hideBottomSheet()
            setupTopAppBar {
                AppbarBasic(
                    title = stringResource(id = R.string.title_appbar_reset_password),
                    onBackPressed = {
                        navigateUp()
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {
            FormInput(
                label = {
                    Text(
                        text = stringResource(id = R.string.label_input_email),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                placeholder = stringResource(R.string.placeholder_email_reset_password),
                initialValue = state.email,
                onChange = {
                    commit { copy(email = it) }
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            ButtonPrimary(
                text = stringResource(R.string.btn_reset_password)
            ) {
                dispatch(ResetPasswordEvent.Submit)
            }
        }
    }


@Preview
@Composable
fun PreviewScreenResetPassword() {
    BaseMainApp {
        ScreenResetPassword(it)
    }
}