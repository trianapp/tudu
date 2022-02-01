package app.trian.tudu.ui.pages.auth

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import app.trian.tudu.common.GoogleAuthContract
import app.trian.tudu.common.Routes
import app.trian.tudu.ui.component.AppbarAuth
import app.trian.tudu.ui.component.ButtonGoogle
import app.trian.tudu.ui.component.ButtonPrimary
import app.trian.tudu.ui.component.task.FormInput
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.UserViewModel
import com.google.android.gms.common.api.ApiException

const val AUTH_GOOGLE_CODE = 1

@Composable
fun PageLogin(
    modifier: Modifier = Modifier,
    router: NavHostController
) {
    val ctx = LocalContext.current
    val userViewModel = hiltViewModel<UserViewModel>()

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
            userViewModel.logInWithGoogle(task){
                    success, message ->
            }
        }
    )

    fun goToDashboard(){
        router.navigate(Routes.DASHBOARD){
            launchSingleTop=true
            popUpTo(Routes.LOGIN){
                inclusive=true
            }
            popUpTo(Routes.ONBOARD){
                inclusive=true
            }
            popUpTo(Routes.SPLASH){
                inclusive=true
            }
            popUpTo(Routes.REGISTER){
                inclusive=true
            }
        }
    }

    fun processLoggedIn(){
        if(email.isBlank() || password.isBlank()){
            Toast.makeText(ctx,ctx.getString(R.string.validation_login),Toast.LENGTH_SHORT).show()
            return
        }
        userViewModel.logInWithEmailAndPassword(email,password){
            success, message ->
            if(success){
                Toast.makeText(ctx,ctx.getString(R.string.signin_success),Toast.LENGTH_LONG).show()
                goToDashboard()
            }else{
                Toast.makeText(ctx,ctx.getString(R.string.signin_failed,message),Toast.LENGTH_LONG).show()
            }
        }
    }

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
                    .padding(horizontal = 36.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(R.string.title_sign_in),
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                )
                Text(
                    text = stringResource(R.string.subtitle_signin),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                    )
                )

                Spacer(modifier = modifier.height(36.dp))
                FormInput(
                    label = {
                        Text(text = stringResource(R.string.label_input_email))
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
                        Text(text = stringResource(R.string.label_input_password))
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
                        text = stringResource(R.string.label_forgot_password)
                    )
                    Spacer(modifier = modifier.width(6.dp))
                    Text(
                        text = stringResource(R.string.btn_reset_here),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.primary
                        )
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
                            text = stringResource(R.string.label_dont_have_account)
                        )
                        Spacer(modifier = modifier.width(6.dp))
                        Text(
                            text = stringResource(R.string.btn_create_account),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.primary
                            )
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
        )
    }
}