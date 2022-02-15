package app.trian.tudu.ui.pages.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.ui.component.AppbarBasic
import app.trian.tudu.ui.component.ButtonPrimary
import app.trian.tudu.ui.component.task.FormInput
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.UserViewModel

/**
 * Page Password
 * author Trian Damai
 * created_at 01/02/22 - 09.40
 * site https://trian.app
 */

@Composable
fun PageResetPassword(
    modifier: Modifier = Modifier,
    router: NavHostController
) {
    val ctx = LocalContext.current
    val userViewModel = hiltViewModel<UserViewModel>()

    var email by remember {
        mutableStateOf("")
    }



    fun proceedChangePassword(){
        if(email.isEmpty()) {
            //todo notify password cannot blank
                Toast.makeText(ctx,"Password cannat empty!",Toast.LENGTH_LONG).show()

            return
        }

        userViewModel.resetPasswordEmail(email){
            success, message ->
            Toast.makeText(ctx,message,Toast.LENGTH_LONG).show()
        }
    }
    Scaffold(
        topBar = {
            AppbarBasic(title = "Reset Password"){
                router.popBackStack()
            }
        }
    ) {
        Column(
            modifier = modifier
                .padding(
                    horizontal = 20.dp
                )
        ) {


            FormInput(
                label = {
                    Text(text = "Email")
                },
                placeholder = "Your email account",
                initialValue = email,
                onChange = {
                    email = it
                }
            )
            Spacer(modifier = modifier.height(30.dp))
            ButtonPrimary(
                text = "Reset my password"
            ){
                proceedChangePassword()
            }
        }
    }
}

@Preview
@Composable
fun PreviewPageResetPassword(){
    TuduTheme {
        PageResetPassword(router = rememberNavController())
    }
}