package app.trian.tudu.ui.pages.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.common.Routes
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

    Scaffold() {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value =email ,
                onValueChange ={
                    email=it
                }
            )
            Spacer(modifier = modifier.height(10.dp))
            TextField(
                value = password,
                onValueChange ={
                    password=it
                }
            )
            Spacer(modifier = modifier.height(10.dp))
            Button(onClick = ::processLoggedIn) {
                Text(text = "Login")
            }
            Spacer(modifier = modifier.height(10.dp))

            Button(onClick = {
                router.navigate(Routes.REGISTER){

                }
            }) {
                Text(text = "Daftar")
            }
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