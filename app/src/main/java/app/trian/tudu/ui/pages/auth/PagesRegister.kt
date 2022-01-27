package app.trian.tudu.ui.pages.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.common.Routes
import app.trian.tudu.ui.theme.TuduTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PagesRegister(
    navHostController: NavHostController
) {
    val coroutine = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit, block = {
        coroutine.launch {
            delay(1000)
            navHostController.navigate(Routes.ONBOARD)
        }
    })
}

@Preview
@Composable
fun PreviewPagesRegister(){
    TuduTheme {
        PagesRegister(rememberNavController())
    }
}