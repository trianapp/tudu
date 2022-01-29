package app.trian.tudu.ui.pages.dashbboard

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import app.trian.tudu.ui.component.TuduBottomNavigation

@ExperimentalMaterial3Api
@Composable
fun BasePagesDashboard(
    router: NavHostController,
    content:@Composable ()->Unit
) {
    Scaffold(bottomBar ={
        TuduBottomNavigation(router = router)
    }) {
        content.invoke()
    }


}