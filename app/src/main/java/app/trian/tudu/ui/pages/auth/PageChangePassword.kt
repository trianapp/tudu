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
import app.trian.tudu.ui.component.FormInput
import app.trian.tudu.ui.component.dialog.DialogLoading
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.UserViewModel

/**
 * Page Password
 * author Trian Damai
 * created_at 01/02/22 - 09.40
 * site https://trian.app
 */

@Composable
fun PageChangePassword(
    modifier: Modifier = Modifier,
    router: NavHostController
) {
    val ctx = LocalContext.current
    val userViewModel = hiltViewModel<UserViewModel>()

    var shouldShowDialogLoading by remember {
        mutableStateOf(false)
    }
    var newPassword by remember {
        mutableStateOf("")
    }

    var confirmNewPassword by remember {
        mutableStateOf("")
    }

    fun proceedChangePassword(){
        if(newPassword.isEmpty()) {
            //todo notify password cannot blank
                Toast.makeText(ctx,"Password cannot empty!",Toast.LENGTH_LONG).show()

            return
        }
        if(newPassword != confirmNewPassword){
            //todo: notify password didn't match
            Toast.makeText(ctx,"Password didn't match!",Toast.LENGTH_LONG).show()
            return
        }
        shouldShowDialogLoading = true
        userViewModel.changePassword(newPassword){
            success, message ->
            shouldShowDialogLoading = false
            Toast.makeText(ctx,message,Toast.LENGTH_LONG).show()
        }
    }
    DialogLoading(
        show = shouldShowDialogLoading
    )
    Scaffold(
        topBar = {
            AppbarBasic(title = "Change Password"){
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
                    Text(text = "New Password")
                },
                placeholder = "Input new password",
                showPasswordObsecure = true,
                initialValue = newPassword,
                onChange = {
                    newPassword=it
                }
            )
            Spacer(modifier = modifier.height(16.dp))
            FormInput(
                label = {
                    Text(text = "Confirm Password")
                },
                placeholder = "Confirm new password",
                showPasswordObsecure = true,
                initialValue = confirmNewPassword,
                onChange = {
                    confirmNewPassword = it
                }
            )
            Spacer(modifier = modifier.height(30.dp))
            ButtonPrimary(
                text = "Save Changes"
            ){
                proceedChangePassword()
            }
        }
    }
}

@Preview
@Composable
fun PreviewPageChangePassword(){
    TuduTheme {
        PageChangePassword(router = rememberNavController())
    }
}