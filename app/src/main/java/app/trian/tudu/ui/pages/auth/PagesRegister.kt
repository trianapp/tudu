package app.trian.tudu.ui.pages.auth

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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
import app.trian.tudu.ui.component.ButtonPrimary
import app.trian.tudu.ui.component.dialog.ModalBottomSheetPrivacyPolicy
import app.trian.tudu.ui.component.FormInput
import app.trian.tudu.ui.component.dialog.DialogLoading
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.ui.theme.fontFamily
import app.trian.tudu.viewmodel.UserViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun PagesRegister(
    modifier: Modifier=Modifier,
    router: NavHostController,
    theme:String
) {
    val ctx = LocalContext.current
    val userViewModel = hiltViewModel<UserViewModel>()
    val systemUiController = rememberSystemUiController()
    val isSystemDark = isSystemInDarkTheme()
    val statusBar = MaterialTheme.colors.background
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmStateChange = {

            true
        }
    )
    val scope = rememberCoroutineScope()

    var shouldShowDialogLoading by remember {
        mutableStateOf(false)
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var username by remember {
        mutableStateOf("")
    }
    var termsConditions by remember {
        mutableStateOf(false)
    }

    val annotatedPrivacyPolicy = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.onBackground,
                fontFamily = fontFamily
            )
        ){
            append(ctx.getString(R.string.text_license_agreement))
        }
        append(" ")
        pushStringAnnotation(
            tag = ctx.getString(R.string.text_privacy_policy),
            annotation = ctx.getString(R.string.text_privacy_policy)
        )
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.primary,
                fontFamily = fontFamily
            )
        ){
            append(ctx.getString(R.string.text_privacy_policy))
        }
        pop()
    }

    val annotatedSignIn = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.onBackground,
                fontFamily = fontFamily
            )
        ){
            append(ctx.getString(R.string.label_already_have_account))
        }
        append(" ")
        pushStringAnnotation(
            tag = ctx.getString(R.string.text_signin),
            annotation = ctx.getString(R.string.text_signin)
        )
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.primary,
                fontFamily = fontFamily
            )
        ){
            append(ctx.getString(R.string.text_signin))
        }
        pop()
    }

    fun processRegister(){
        if(email.isBlank() || password.isBlank() || username.isBlank()){
            Toast.makeText(ctx,ctx.getString(R.string.alert_input_empty),Toast.LENGTH_SHORT).show()
            return
        }
        if(!email.isEmailValid()){
            ctx.toastError(ctx.getString(R.string.alert_validation_email))
            return
        }
        shouldShowDialogLoading = true
        userViewModel.registerWithEmailAndPassword(username,email,password){
            success, message ->
            shouldShowDialogLoading = false
            if(success){
                router.popBackStack()
            }
            Toast.makeText(ctx,"$message", Toast.LENGTH_LONG).show()
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
        show=shouldShowDialogLoading
    )

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            ModalBottomSheetPrivacyPolicy(){
                scope.launch {
                    bottomSheetState.hide()
                }
            }
        }
    ) {
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
                    .padding(
                        horizontal = 20.dp
                    )
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = stringResource(R.string.title_register),
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Row {
                    ClickableText(
                        text = annotatedSignIn,
                        onClick = {
                                offset->
                            annotatedSignIn.getStringAnnotations(
                                tag = ctx.getString(R.string.text_signin),
                                start = offset,
                                end = offset
                            ).firstOrNull()?.let {
                                    annotated ->
                                router.navigate(Routes.LOGIN){
                                    popUpTo(Routes.REGISTER){
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            }
                        }
                    )
                }
                Spacer(modifier = modifier.height(20.dp))
                Column(
                    modifier= modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    FormInput(
                        initialValue = username,
                        label = {
                            Text(
                                text = stringResource(R.string.label_input_username),
                                style = MaterialTheme.typography.body2
                            )
                        },
                        placeholder = stringResource(R.string.placeholder_input_username),
                        onChange = {
                            username = it
                        }
                    )
                    Spacer(modifier = modifier.height(16.dp))
                    FormInput(
                        initialValue = email,
                        label = {
                            Text(
                                text = stringResource(id = R.string.label_input_email),
                                style = MaterialTheme.typography.body2
                            )
                        },
                        placeholder = stringResource(id = R.string.placeholder_input_email),
                        onChange = {
                            email = it
                        }
                    )
                    Spacer(modifier = modifier.height(16.dp))
                    FormInput(
                        initialValue = password,
                        label = {
                            Text(
                                text = stringResource(id = R.string.label_input_password),
                                style = MaterialTheme.typography.body2
                            )
                        },
                        showPasswordObsecure = true,
                        placeholder = stringResource(id = R.string.placeholder_input_password),
                        onChange = {
                            password = it
                        }
                    )
                }
                Spacer(modifier = modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = termsConditions,
                        onCheckedChange ={
                            termsConditions = it
                            ctx.hideKeyboard()
                        },
                        modifier=modifier.clip(CircleShape)
                    )


                    ClickableText(
                        text = annotatedPrivacyPolicy,
                        onClick = {
                                offset->
                            annotatedPrivacyPolicy.getStringAnnotations(
                                tag = ctx.getString(R.string.text_privacy_policy),
                                start = offset,
                                end = offset
                            ).firstOrNull()?.let { _ ->
                                scope.launch {
                                    bottomSheetState.show()
                                }

                            }
                        }
                    )

                }
                Spacer(modifier = modifier.height(20.dp))
                ButtonPrimary(
                    text = stringResource(R.string.btn_continue),
                    enabled = termsConditions

                ){
                    processRegister()
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewPagesRegister(){
    TuduTheme {
        PagesRegister(
            router=rememberNavController(),
            theme = ThemeData.DEFAULT.value
        )
    }
}