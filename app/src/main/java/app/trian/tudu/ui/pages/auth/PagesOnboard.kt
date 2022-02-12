package app.trian.tudu.ui.pages.auth

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import app.trian.tudu.common.signInInSuccessOnboard
import app.trian.tudu.ui.component.ButtonGoogle
import app.trian.tudu.ui.component.ButtonPrimary
import app.trian.tudu.ui.component.ButtonSecondary
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.UserViewModel


@Composable
fun PagesOnboard(
    modifier: Modifier=Modifier,
    router:NavHostController
){
    val userViewModel = hiltViewModel<UserViewModel>()
    val ctx = LocalContext.current
    val isDark = isSystemInDarkTheme()

    fun goToDashboard(){
        router.signInInSuccessOnboard()
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

        }
    }
}

@Preview
@Composable
fun PreviewOnboard(){
    TuduTheme {
        PagesOnboard(router = rememberNavController())
    }
}