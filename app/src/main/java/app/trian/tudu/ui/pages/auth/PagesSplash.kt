package app.trian.tudu.ui.pages.auth

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.R
import app.trian.tudu.common.Routes
import app.trian.tudu.common.getLogo
import app.trian.tudu.common.getTheme
import app.trian.tudu.domain.ThemeData
import app.trian.tudu.ui.component.dialog.DialogTimeFormat
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.UserViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

@Composable
fun PagesSplash(
    modifier: Modifier=Modifier,
    router: NavHostController,
    theme:String
) {

    val userViewModel = hiltViewModel<UserViewModel>()
    val systemUiController = rememberSystemUiController()
    val isSystemDark = isSystemInDarkTheme()
    val statusBar = MaterialTheme.colors.background
    LaunchedEffect(key1 = Unit, block = {
        //cek if user already logged in
        userViewModel.userAlreadyLogin {
            if(it){
                router.navigate(Routes.DASHBOARD){
                    popUpTo(Routes.SPLASH){
                        inclusive=true
                    }
                }
            }else{
                router.navigate(Routes.ONBOARD){
                    popUpTo(Routes.SPLASH){
                        inclusive=true
                    }
                }
            }

        }
    })

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


    Scaffold() {
        Column(
            modifier=modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = when(theme.getTheme()){
                    ThemeData.DEFAULT -> isSystemInDarkTheme()
                    ThemeData.DARK -> true
                    ThemeData.LIGHT -> false
                }.getLogo()),
                contentDescription = stringResource(R.string.content_description_logo))

        }
    }
}


@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewSplash(){
    TuduTheme {
        PagesSplash(
            router = rememberNavController(),
            theme = ThemeData.DEFAULT.value
        )
    }
}