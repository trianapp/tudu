package app.trian.tudu.ui.pages.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.common.Routes
import app.trian.tudu.ui.theme.TuduTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun PagesSplash(
    modifier: Modifier=Modifier,
   navHostController: NavHostController
) {
    val coroutine = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit, block = {
        coroutine.launch {
            delay(1000)
            navHostController.navigate(Routes.ONBOARD){
                popUpTo(route = Routes.SPLASH)
            }
        }
    })

    Scaffold {
        Column(
            modifier=modifier.fillMaxSize()
        ) {
            Text(text = "Ini Splash")
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewSplash(){
    TuduTheme {
        PagesSplash(navHostController = rememberNavController())
    }
}