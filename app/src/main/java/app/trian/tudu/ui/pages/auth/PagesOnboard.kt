package app.trian.tudu.ui.pages.auth

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
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
import app.trian.tudu.common.GoogleAuthContract
import app.trian.tudu.common.Routes
import app.trian.tudu.common.signInInSuccessOnboard
import app.trian.tudu.ui.component.ButtonGoogle
import app.trian.tudu.ui.component.ButtonPrimary
import app.trian.tudu.ui.component.ButtonSecondary
import app.trian.tudu.ui.component.dialog.ModalBottomSheetPrivacyPolicy
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.UserViewModel
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@Composable
fun PagesOnboard(
    modifier: Modifier=Modifier,
    router:NavHostController
){
    val userViewModel = hiltViewModel<UserViewModel>()
    val ctx = LocalContext.current
    val isDark = isSystemInDarkTheme()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmStateChange = {

            true
        }
    )
    val scope = rememberCoroutineScope()

    fun goToDashboard(){
        router.signInInSuccessOnboard()
    }

    val annotatedPrivacyPolicy = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.onBackground
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
                color = MaterialTheme.colors.primary
            )
        ){
            append(ctx.getString(R.string.text_privacy_policy))
        }
        pop()
    }
    val googleAuthLauncher = rememberLauncherForActivityResult(
        contract = GoogleAuthContract(),
        onResult = {
                task->
            userViewModel.logInWithGoogle(task){
                success, message ->

                if(success){
                    Toast.makeText(ctx,ctx.getString(R.string.signin_success),Toast.LENGTH_LONG).show()
                    goToDashboard()
                }else{
                    Toast.makeText(ctx,ctx.getString(R.string.signin_failed,message),Toast.LENGTH_LONG).show()
                }
            }
        }
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
        Scaffold {
            Column(
                modifier= modifier
                    .fillMaxSize()
                    .padding(horizontal = 36.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween

            ) {
                Spacer(modifier = modifier.height(60.dp))
                Image(
                    modifier=modifier.size(280.dp),
                    painter = painterResource(id = if(isDark) R.drawable.ilustrasion_dark else R.drawable.ilustrasion_light ),
                    contentDescription = stringResource(R.string.content_description_image_onboard)
                )
                Spacer(modifier = modifier.height(20.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.title_onboard),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Box(modifier=modifier.wrapContentWidth()) {
                        Text(
                            modifier = modifier
                                .align(Alignment.TopCenter),
                            text = stringResource(R.string.subtitle_onboard),
                            style = TextStyle(
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colors.primary
                            )
                        )
                        Image(
                            modifier= modifier
                                .align(Alignment.BottomEnd)
                                .padding(top = 18.dp),
                            painter = painterResource(id =R.drawable.ic_onboard_splash),
                            contentDescription = stringResource(R.string.content_description_slash_onboard)
                        )
                    }
                }
                Spacer(modifier = modifier.height(10.dp))
                Column {
                    ButtonPrimary(text = stringResource(id = R.string.btn_signin)){
                        router.navigate(Routes.LOGIN){
                            launchSingleTop=true
                        }
                    }
                    Spacer(modifier = modifier.height(16.dp))
                    ButtonSecondary(text = stringResource(R.string.btn_create_new_account)){
                        router.navigate(Routes.REGISTER){
                            launchSingleTop=true
                        }
                    }
                    Spacer(modifier = modifier.height(16.dp))
                    ButtonGoogle(text = stringResource(id = R.string.btn_login_google)){
                        googleAuthLauncher.launch(AUTH_GOOGLE_CODE)
                    }

                }

                Spacer(modifier = modifier.height(16.dp))
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
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewOnboard(){
    TuduTheme {
        PagesOnboard(router = rememberNavController())
    }
}