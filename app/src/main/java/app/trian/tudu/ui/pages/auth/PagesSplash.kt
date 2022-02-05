package app.trian.tudu.ui.pages.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.common.Routes
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.UserViewModel
import kotlinx.coroutines.delay

@Composable
fun PagesSplash(
    modifier: Modifier=Modifier,
    router: NavHostController
) {
    val userViewModel = hiltViewModel<UserViewModel>()
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

    Scaffold() {
        Column(
            modifier=modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "tudu app")
            CircularProgressIndicator()
        }
    }
}


@Preview
@Composable
fun PreviewSplash(){
    TuduTheme {
        PagesSplash(router = rememberNavController())
    }
}