package app.trian.tudu.feature.auth.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.trian.tudu.ApplicationState
import app.trian.tudu.R.string
import app.trian.tudu.base.BaseMainApp
import app.trian.tudu.base.UIWrapper
import app.trian.tudu.base.extensions.navigateUp
import app.trian.tudu.components.AnnotationTextItem
import app.trian.tudu.components.AppbarAuth
import app.trian.tudu.components.ButtonPrimary
import app.trian.tudu.components.CheckBoxWithAction
import app.trian.tudu.components.DialogLoading
import app.trian.tudu.components.FormInput
import app.trian.tudu.components.TextWithAction
import app.trian.tudu.feature.auth.signin.SignIn

object SignUp {
    const val routeName = "SignUp"
}

fun NavGraphBuilder.routeSignUp(
    state: ApplicationState,
) {
    composable(SignUp.routeName) {
        ScreenSignUp(state = state)

    }
}

@Composable
internal fun ScreenSignUp(
    state: ApplicationState,
) = UIWrapper<SignUpViewModel>(appState = state) {
    val privacyPolicy = listOf(
        AnnotationTextItem.Text(stringResource(id = string.text_license_agreement)),
        AnnotationTextItem.Button(stringResource(id = string.text_privacy_policy))
    )
    val signInText = listOf(
        AnnotationTextItem.Text(stringResource(id = string.label_already_have_account)),
        AnnotationTextItem.Button(stringResource(id = string.text_signin))
    )
    val uiState by uiState.collectAsState()
    with(state) {
        setupTopAppBar {
            AppbarAuth(
                onBackPressed = {
                    navigateUp()
                }
            )
        }
    }
    DialogLoading(
        show = uiState.isLoading
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 20.dp
            )
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(string.title_register),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        Row {
            TextWithAction(
                labels = signInText,
                onTextClick = {
                    if (it == 1) {
                        navigateAndReplaceAll(SignIn.routeName)
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            FormInput(
                initialValue = uiState.displayName,
                label = {
                    Text(
                        text = stringResource(string.label_input_display_nama),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                placeholder = stringResource(string.placeholder_input_display_name),
                onChange = {
                    commit { copy(displayName = it) }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            FormInput(
                initialValue = uiState.email,
                label = {
                    Text(
                        text = stringResource(id = string.label_input_email),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                placeholder = stringResource(id = string.placeholder_input_email),
                onChange = {
                    commit { copy(email = it) }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            FormInput(
                initialValue = uiState.password,
                label = {
                    Text(
                        text = stringResource(id = string.label_input_password),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                showPasswordObsecure = true,
                placeholder = stringResource(id = string.placeholder_input_password),
                onChange = {
                    commit { copy(password = it) }
                }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CheckBoxWithAction(
                labels = privacyPolicy,
                checked = uiState.agreeTnc,
                onTextClick = {},
                onCheckedChange = {
                    commit { copy(agreeTnc = it) }
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        ButtonPrimary(
            text = stringResource(string.btn_continue),
            enabled = uiState.agreeTnc

        ) {
            dispatch(SignUpEvent.SignUpWithEmail)
        }
    }
}


@Preview
@Composable
fun PreviewScreenSignUp() {
    BaseMainApp {
        ScreenSignUp(it)
    }
}