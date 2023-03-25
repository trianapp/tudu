package app.trian.tudu.feature.auth.signin

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
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
import app.trian.tudu.base.contract.GoogleAuthContract
import app.trian.tudu.base.extensions.backPressedAndClose
import app.trian.tudu.components.AnnotationTextItem
import app.trian.tudu.components.AppbarAuth
import app.trian.tudu.components.ButtonPrimary
import app.trian.tudu.components.ButtonSocial
import app.trian.tudu.components.DialogLoading
import app.trian.tudu.components.FormInput
import app.trian.tudu.components.TextWithAction
import app.trian.tudu.feature.auth.resetPassword.ResetPassword
import app.trian.tudu.feature.auth.signup.SignUp

object SignIn {
    const val routeName = "SignIn"
}

fun NavGraphBuilder.routeSignIn(
    state: ApplicationState,
) {
    composable(SignIn.routeName) {
        ScreenSignIn(appState = state)
    }
}


@Composable
internal fun ScreenSignIn(
    appState: ApplicationState,
) = UIWrapper<SignInViewModel>(appState = appState) {
    val forgetPasswordText = listOf(
        AnnotationTextItem.Text(stringResource(id = string.label_forgot_password)),
        AnnotationTextItem.Button(stringResource(id = string.btn_reset_here))
    )

    val signUp = listOf(
        AnnotationTextItem.Text(stringResource(id = string.label_dont_have_account)),
        AnnotationTextItem.Button(stringResource(id = string.btn_create_new_account))
    )

    val uiState by uiState.collectAsState()

    val signInGoogle = rememberLauncherForActivityResult(
        contract = GoogleAuthContract(),
        onResult = {
            dispatch(SignInEvent.SignInWithGoogle(it))
        }
    )

    with(appState) {
        setupTopAppBar {
            AppbarAuth(
                onBackPressed = {
                    backPressedAndClose()
                }
            )
        }
    }

    DialogLoading(
        show = uiState.isLoading,
        message = "Signing in...",
        title = "Please wait"
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp
                ),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(string.title_sign_in),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(string.subtitle_signin),
                style = MaterialTheme.typography.displayMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(36.dp))
            FormInput(
                label = {
                    Text(
                        text = stringResource(string.label_input_email),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                initialValue = uiState.email,
                placeholder = stringResource(string.placeholder_input_email),
                onChange = {
                    commit { copy(email = it) }
                }

            )
            Spacer(modifier = Modifier.height(10.dp))
            FormInput(
                label = {
                    Text(
                        text = stringResource(string.label_input_password),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                initialValue = uiState.password,
                placeholder = stringResource(string.placeholder_input_password),
                onChange = {
                    commit { copy(password = it) }
                },
                showPasswordObsecure = true
            )
            Spacer(modifier = Modifier.height(30.dp))
            ButtonPrimary(text = stringResource(id = string.btn_signin)) {
                dispatch(SignInEvent.SignInWithEmail)
            }
            Spacer(modifier = Modifier.height(10.dp))
            TextWithAction(
                labels = forgetPasswordText,
                onTextClick = {
                    if (it == 1) {
                        navigateSingleTop(ResetPassword.routeName)
                    }
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            Column {
                Divider()
                Spacer(modifier = Modifier.height(36.dp))
                Column(
                    modifier = Modifier.padding(horizontal = 36.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ButtonSocial(text = stringResource(id = string.btn_login_google), fullWidth = true) {
                        signInGoogle.launch(1)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    TextWithAction(
                        labels = signUp,
                        onTextClick = {
                            if (it == 1) {
                                navigateSingleTop(SignUp.routeName)
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}


@Preview
@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewScreenSignIn() {
    BaseMainApp(
        topAppBar = {
            AppbarAuth()
        }
    ) {
        ScreenSignIn(it)
    }
}