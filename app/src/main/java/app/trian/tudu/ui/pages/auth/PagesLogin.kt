package app.trian.tudu.ui.pages.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.common.Routes
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.UserViewModel

@ExperimentalMaterial3Api
@Composable
fun PagesLogin(
    modifier: Modifier=Modifier,
    navHostController: NavHostController
) {
    val vm = hiltViewModel<UserViewModel>()
    val counter  by vm.counter.observeAsState(initial = 0)

    Scaffold {
        Column(
            modifier=modifier.fillMaxSize()
        ) {
            BasicTextField(value = "", onValueChange = {
                
            })
            BasicTextField(value = "", onValueChange = {

            })
            Button(onClick = { navHostController.navigate(Routes.DASHBOARD) }) {
                Text(text = "Login")
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewPagesLogin(){
    TuduTheme {
        PagesLogin(navHostController = rememberNavController())
    }
}