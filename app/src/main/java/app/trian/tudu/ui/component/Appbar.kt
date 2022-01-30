package app.trian.tudu.ui.component

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.ui.theme.TuduTheme
import compose.icons.Octicons
import compose.icons.octicons.ArrowLeft16
import compose.icons.octicons.Share24

/**
 * App bar home
 * author Trian Damai
 * created_at 29/01/22 - 19.01
 * site https://trian.app
 */
@Composable
fun AppbarHome(
    onAction:()->Unit
) {
    TopAppBar(
        title = {
                Text(text = "Task")
        },
        actions = {
                  IconToggleButton(checked = false, onCheckedChange = {
                      onAction()
                  }) {
                      Icon(imageVector = Octicons.Share24, contentDescription = "")
                  }
        },
        navigationIcon = {}
    )
}
@Composable
fun AppbarAuth(
    modifier: Modifier=Modifier,
    onBackPressed:()->Unit={}
){
    TopAppBar(
        backgroundColor = MaterialTheme.colorScheme.background,
        elevation = 0.dp,
        navigationIcon = {
            IconToggleButton(checked = false, onCheckedChange = {
                onBackPressed()
            }) {
                androidx.compose.material3.Icon(
                    imageVector = Octicons.ArrowLeft16,
                    contentDescription = ""
                )
            }
        },
        title = {}
    )
}

@Preview
@Composable
fun PreviewAppbarHome(){
    TuduTheme {
        AppbarHome(onAction = {})
    }
}