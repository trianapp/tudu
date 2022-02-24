package app.trian.tudu.ui.pages.auth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.R
import app.trian.tudu.common.*
import app.trian.tudu.domain.ThemeData
import app.trian.tudu.ui.component.AppbarAuth
import app.trian.tudu.ui.component.ButtonGoogle
import app.trian.tudu.ui.component.ButtonPrimary
import app.trian.tudu.ui.component.FormInput
import app.trian.tudu.ui.component.dialog.DialogLoading
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.UserViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

const val AUTH_GOOGLE_CODE = 1

@Composable
fun PageLogin(
    modifier: Modifier = Modifier,
    router: NavHostController,
    theme:String
) {
    val ctx = LocalContext.current
    val userViewModel = hiltViewModel<UserViewModel>()
    val systemUiController = rememberSystemUiController()
    val isSystemDark = isSystemInDarkTheme()
    val statusBar = MaterialTheme.colors.background

    var shouldShowDialogLoading by remember {
        mutableStateOf(false)
    }

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val googleAuthLauncher = rememberLauncherForActivityResult(
        contract = GoogleAuthContract(),
        onResult = {
                task->
            shouldShowDialogLoading = true
            userViewModel.logInWithGoogle(task){
                    success, message ->
                shouldShowDialogLoading = false
                if(success){
                    ctx.toastSuccess(ctx.getString(R.string.signin_success))
                    router.signInInSuccess()
                }else{
                    ctx.toastError(ctx.getString(R.string.signin_failed,message))
                }
            }
        }
    )



    fun processLoggedIn(){
        ctx.hideKeyboard()
        if(email.isBlank() || password.isBlank()){
            ctx.toastError(ctx.getString(R.string.validation_login))
            return
        }
        if(!email.isEmailValid()){
            ctx.toastError(ctx.getString(R.string.alert_validation_email))
            return
        }
        shouldShowDialogLoading = true
        userViewModel.logInWithEmailAndPassword(email,password){
            success, message ->
            shouldShowDialogLoading = false
            if(success){
                ctx.toastSuccess(ctx.getString(R.string.signin_success))
                router.signInInSuccess()
            }else{
                ctx.toastError(ctx.getString(R.string.signin_failed,message))
            }
        }
    }
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = statusBar,
            darkIcons = when(theme.getTheme()){
                ThemeData.DEFAULT -> !isSystemDark
                ThemeData.DARK -> false
                ThemeData.LIGHT -> true
            }
        )
    }

    DialogLoading(
        show = shouldShowDialogLoading
    )

    Scaffold(
        topBar = {
            AppbarAuth(){
                router.popBackStack()
            }
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier= modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(R.string.title_sign_in),
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                )
                Text(
                    text = stringResource(R.string.subtitle_signin),
                    style = MaterialTheme.typography.caption.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                    )
                )

                Spacer(modifier = modifier.height(36.dp))
                FormInput(
                    label = {
                        Text(
                            text = stringResource(R.string.label_input_email),
                            style = MaterialTheme.typography.body2
                        )
                    },
                    initialValue = email,
                    placeholder = stringResource(R.string.placeholder_input_email),
                    onChange = {
                        email=it
                    }

                )
                Spacer(modifier = modifier.height(10.dp))
                FormInput(
                    label = {
                        Text(
                            text = stringResource(R.string.label_input_password),
                            style = MaterialTheme.typography.body2
                        )
                    },
                    initialValue = password,
                    placeholder = stringResource(R.string.placeholder_input_password),
                    onChange = {
                        password = it
                    },
                    showPasswordObsecure = true
                )
                Spacer(modifier = modifier.height(30.dp))

                ButtonPrimary(text = stringResource(R.string.btn_signin)){
                    processLoggedIn()
                }
                Spacer(modifier = modifier.height(10.dp))
                Row(
                    modifier=modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.label_forgot_password),
                        style = MaterialTheme.typography.body2
                    )
                    Spacer(modifier = modifier.width(6.dp))
                    Text(
                        text = stringResource(R.string.btn_reset_here),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colors.primary
                        ),
                        modifier = modifier
                            .clickable {
                                router.navigate(Routes.RESET_PASSWORD){
                                    launchSingleTop = true
                                }
                            }
                    )
                }
            }

            Column {
                Divider()
                Spacer(modifier = modifier.height(36.dp))
                Column(
                    modifier = modifier.padding(horizontal = 36.dp)
                ) {
                    ButtonGoogle(text = stringResource(R.string.btn_login_google)){
                        googleAuthLauncher.launch(AUTH_GOOGLE_CODE)
                    }
                    Spacer(modifier = modifier.height(20.dp))
                    Row(
                        modifier=modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.label_dont_have_account),
                            style = MaterialTheme.typography.caption
                        )
                        Spacer(modifier = modifier.width(6.dp))
                        Text(
                            text = stringResource(R.string.btn_create_account),
                            style =  MaterialTheme.typography.caption.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colors.primary
                            ),
                            modifier = modifier.clickable {
                                router.navigate(Routes.REGISTER){
                                    launchSingleTop=true
                                }
                            }
                        )
                    }
                }
            }
            Spacer(modifier = modifier.height(10.dp))


        }
    }

}



@Preview
@Composable
fun PreviewPagesLogin(){
    TuduTheme {
        PageLogin(
            router = rememberNavController(),
            theme = ThemeData.DEFAULT.value
        )
    }
}