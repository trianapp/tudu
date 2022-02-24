package app.trian.tudu.ui.pages.auth

import androidx.compose.foundation.isSystemInDarkTheme
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
import app.trian.tudu.common.getTheme
import app.trian.tudu.common.toastError
import app.trian.tudu.common.toastSuccess
import app.trian.tudu.domain.ThemeData
import app.trian.tudu.ui.component.AppbarBasic
import app.trian.tudu.ui.component.ButtonPrimary
import app.trian.tudu.ui.component.FormInput
import app.trian.tudu.ui.component.dialog.DialogLoading
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.UserViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Page Password
 * author Trian Damai
 * created_at 01/02/22 - 09.40
 * site https://trian.app
 */

@Composable
fun PageChangePassword(
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
    var newPassword by remember {
        mutableStateOf("")
    }

    var confirmNewPassword by remember {
        mutableStateOf("")
    }

    fun proceedChangePassword(){
        if(newPassword.isEmpty()) {
            //todo notify password cannot blank
                ctx.toastError("Password cannot empty!")

            return
        }
        if(newPassword != confirmNewPassword){
            //todo: notify password didn't match
            ctx.toastError("Password didn't match!")
            return
        }
        shouldShowDialogLoading = true
        userViewModel.changePassword(newPassword){
            success, message ->
            shouldShowDialogLoading = false
            if(success){
                ctx.toastSuccess(message)
            }else{
                ctx.toastError(message)
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
            AppbarBasic(title = stringResource(R.string.title_appbar_change_password)){
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
                    Text(text = stringResource(R.string.label_input_new_password))
                },
                placeholder = stringResource(R.string.placeholder_input_new_password),
                showPasswordObsecure = true,
                initialValue = newPassword,
                onChange = {
                    newPassword=it
                }
            )
            Spacer(modifier = modifier.height(16.dp))
            FormInput(
                label = {
                    Text(text = stringResource(R.string.label_input_confirm_new_password))
                },
                placeholder = stringResource(R.string.placeholder_input_confirm_new_password),
                showPasswordObsecure = true,
                initialValue = confirmNewPassword,
                onChange = {
                    confirmNewPassword = it
                }
            )
            Spacer(modifier = modifier.height(30.dp))
            ButtonPrimary(
                text = stringResource(R.string.btn_save_changes)
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
        PageChangePassword(
            router = rememberNavController(),
            theme = ThemeData.DEFAULT.value
        )
    }
}