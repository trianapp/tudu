package app.trian.tudu.ui.pages.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.common.Routes
import app.trian.tudu.ui.theme.TuduTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun PagesOnboard(
    modifier: Modifier=Modifier,
    router:NavHostController
){

    Scaffold {
        Column(
            modifier=modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Ini Onboard")
            Button(onClick = {
                router.navigate(Routes.LOGIN){
                    popUpTo(Routes.ONBOARD){
                        inclusive=true
                    }
                }
            }) {
                Text(text = "Login")
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