package app.trian.tudu.ui.pages.dashbboard

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasePagesDashboard(
    content:@Composable ()->Unit
) {

    content.invoke()

}