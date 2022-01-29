package app.trian.tudu.ui.pages.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.common.Routes
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PagesRegister(
    modifier: Modifier=Modifier,
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

    fun processRegister(){
        userViewModel.registerWithEmailAndPassword(email,password){
            success, message ->
            if(success){
                router.popBackStack()
            }
            Toast.makeText(ctx,"login $success $message", Toast.LENGTH_LONG).show()
        }
    }

    Scaffold {
        Column(
            modifier = modifier.fillMaxSize(),
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
            Button(onClick =::processRegister
            ) {
                Text(text = "Daftar")
            }
            Spacer(modifier = modifier.height(10.dp))

            Button(onClick = {
                router.popBackStack()
            }) {
                Text(text = "Masuk")
            }
        }
    }
}

@Preview
@Composable
fun PreviewPagesRegister(){
    TuduTheme {
        PagesRegister(router=rememberNavController())
    }
}