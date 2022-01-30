package app.trian.tudu.ui.pages.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.common.Routes
import app.trian.tudu.ui.component.AppbarAuth
import app.trian.tudu.ui.component.ButtonGoogle
import app.trian.tudu.ui.component.ButtonPrimary
import app.trian.tudu.ui.component.FormInput
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.UserViewModel


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

    fun processLoggedIn(){
        if(email.isBlank() || password.isBlank()){
            Toast.makeText(ctx,"Please fille email and password!",Toast.LENGTH_SHORT).show()
            return
        }
        userViewModel.loggedInWithEmailAndPassword(email,password){
            success, message ->
            Toast.makeText(ctx,"login $success $message",Toast.LENGTH_LONG).show()
            if(success){
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
                    text = "Sign in your account",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                )
                Text(
                    text = "login to write a tudu list for you",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                    )
                )

                Spacer(modifier = modifier.height(36.dp))
                FormInput(
                    label = {
                        Text(text = "Email")
                    },
                    initialValue = email,
                    placeholder = "example@triap.app",
                    onChange = {
                        email=it
                    }
                )
                Spacer(modifier = modifier.height(10.dp))
                FormInput(
                    label = {
                        Text(text = "Password")
                    },
                    initialValue = password,
                    placeholder = "Your password",
                    onChange = {
                        password = it
                    }
                )
                Spacer(modifier = modifier.height(30.dp))

                ButtonPrimary(text = "Sign In"){
                    processLoggedIn()
                }
                Spacer(modifier = modifier.height(10.dp))
                Row(
                    modifier=modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Forget password?"
                    )
                    Spacer(modifier = modifier.width(6.dp))
                    Text(
                        text = "Reset here",
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
                    ButtonGoogle(text = "Continue With Google")
                    Spacer(modifier = modifier.height(20.dp))
                    Row(
                        modifier=modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Don't have account?"
                        )
                        Spacer(modifier = modifier.width(6.dp))
                        Text(
                            text = "Create one",
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