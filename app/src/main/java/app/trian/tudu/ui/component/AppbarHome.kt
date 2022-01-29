package app.trian.tudu.ui.component

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.trian.tudu.ui.theme.TuduTheme
import compose.icons.Octicons
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

@Preview
@Composable
fun PreviewAppbarHome(){
    TuduTheme {
        AppbarHome(onAction = {})
    }
}