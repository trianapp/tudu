package app.trian.tudu.ui.pages.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.R
import app.trian.tudu.common.Routes
import app.trian.tudu.ui.component.ButtonGoogle
import app.trian.tudu.ui.component.ButtonPrimary
import app.trian.tudu.ui.component.ButtonSecondary
import app.trian.tudu.ui.theme.TuduTheme
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun PagesOnboard(
    modifier: Modifier=Modifier,
    router:NavHostController
){



    Scaffold {
        Column(
            modifier= modifier
                .fillMaxSize()
                .padding(horizontal = 36.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween

        ) {

            Image(
                modifier=modifier.size(280.dp),
                painter = painterResource(id = R.drawable.ic_onboard),
                contentDescription = ""
            )
            Spacer(modifier = modifier.height(40.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Better way to manage",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                Box(modifier=modifier.wrapContentWidth()) {
                    Text(
                        modifier = modifier
                            .align(Alignment.TopCenter),
                        text = "your task.",
                        style = TextStyle(
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Image(
                        modifier= modifier
                            .align(Alignment.BottomEnd)
                            .padding(top = 18.dp),
                        painter = painterResource(id =R.drawable.ic_onboard_slash),
                        contentDescription = ""
                    )
                }
            }
            Spacer(modifier = modifier.height(10.dp))
            Column {
                ButtonPrimary(text = "Sign In"){
                    router.navigate(Routes.LOGIN){
                        launchSingleTop=true
                    }
                }
                Spacer(modifier = modifier.height(16.dp))
                ButtonSecondary(text = "Create New Account"){
                    router.navigate(Routes.REGISTER){
                        launchSingleTop=true
                    }
                }
                Spacer(modifier = modifier.height(16.dp))
                ButtonGoogle(text = "Continue With google"){

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