package app.trian.tudu.ui.pages.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.R
import app.trian.tudu.common.toastError
import app.trian.tudu.common.toastSuccess
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
fun PageResetPassword(
    modifier: Modifier = Modifier,
    router: NavHostController
) {
    val ctx = LocalContext.current
    val userViewModel = hiltViewModel<UserViewModel>()
    var shouldShowDialogLoading by remember {
        mutableStateOf(false)
    }

    var email by remember {
        mutableStateOf("")
    }



    fun proceedChangePassword(){
        if(email.isEmpty()) {
            //todo notify password cannot blank
                ctx.toastError("Password cannot empty!")

            return
        }

        shouldShowDialogLoading = true
        userViewModel.resetPasswordEmail(email){
            success, message ->
            shouldShowDialogLoading = false
            if(success){
                ctx.toastSuccess(message)
            }else{
                ctx.toastError(message)
            }
        }
    }
    DialogLoading(
        show = shouldShowDialogLoading
    )
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
                    Text(
                        text = stringResource(id = R.string.label_input_email),
                        style = MaterialTheme.typography.body2
                    )
                },
                placeholder = stringResource(R.string.placeholder_email_reset_password),
                initialValue = email,
                onChange = {
                    email = it
                }
            )
            Spacer(modifier = modifier.height(30.dp))
            ButtonPrimary(
                text = stringResource(R.string.btn_reset_password)
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