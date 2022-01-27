package app.trian.tudu.ui.pages.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.UserViewModel

@Composable
fun PagesLogin() {
    val vm = hiltViewModel<UserViewModel>()
    val counter  by vm.counter.observeAsState(initial = 0)
    Column {
        Text(text = "ini tes $counter")
        Button(onClick = { vm.increment() }) {
            Text(text = "ADD")
        }
    }
}

@Preview
@Composable
fun PreviewPagesLogin(){
    TuduTheme {
        PagesLogin()
    }
}