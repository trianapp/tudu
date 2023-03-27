package app.trian.tudu.feature.editProfile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.trian.tudu.R
import app.trian.tudu.ApplicationState
import app.trian.tudu.base.BaseMainApp
import app.trian.tudu.base.UIWrapper
import app.trian.tudu.base.extensions.hideKeyboard
import app.trian.tudu.base.extensions.navigateUp
import app.trian.tudu.components.AppbarBasic
import app.trian.tudu.components.ButtonPrimary
import app.trian.tudu.components.DialogLoading
import app.trian.tudu.components.FormInput

object EditProfile {
    const val routeName = "EditProfile"
}

fun NavGraphBuilder.routeEditProfile(
    state: ApplicationState,
) {
    composable(EditProfile.routeName) {
        ScreenEditProfile(appState = state)
    }
}

@Composable
internal fun ScreenEditProfile(
    appState: ApplicationState,
) = UIWrapper<EditProfileViewModel>(appState = appState) {
    val state by uiState.collectAsState()
    val ctx = LocalContext.current
    with(appState) {
        hideBottomAppBar()
        hideBottomSheet()
        setupTopAppBar {
            AppbarBasic(
                title = stringResource(R.string.title_appbar_edit_profile),
                onBackPressed = {
                    navigateUp()
                }
            )
        }
    }

    DialogLoading(
        show = state.isLoading
    )
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
    ) {
        FormInput(
            label = {
                Text(
                    text = stringResource(R.string.label_input_diplay_name),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            placeholder = stringResource(R.string.label_placeholder_input_display_name),
            initialValue = state.displayName,
            onChange = {
                commit { copy(displayName = it) }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    ctx.hideKeyboard()
                    dispatch(EditProfileEvent.Submit)
                }
            )
        )
        Spacer(modifier = Modifier.height(30.dp))
        ButtonPrimary(
            text = stringResource(id = R.string.btn_save_changes)
        ) {
            ctx.hideKeyboard()
            dispatch(EditProfileEvent.Submit)
        }
    }
}


@Preview
@Composable
fun PreviewScreenEditProfile() {
    BaseMainApp {
        ScreenEditProfile(it)
    }
}