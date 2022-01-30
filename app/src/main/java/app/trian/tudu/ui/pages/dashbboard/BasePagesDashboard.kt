package app.trian.tudu.ui.pages.dashbboard

import android.annotation.SuppressLint
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import app.trian.tudu.ui.component.TuduBottomNavigation

@SuppressLint("ServiceCast")
@ExperimentalMaterialApi
@Composable
fun BasePagesDashboard(
    router: NavHostController,
    modalBottomSheetState:ModalBottomSheetState,
    topAppbar:@Composable ()->Unit={},
    sheetContent:@Composable ()->Unit={},
    content:@Composable ()->Unit
) {
    ModalBottomSheetLayout(
        sheetState =modalBottomSheetState,
        sheetContent = {
            sheetContent.invoke()
        }
    ) {
        Scaffold(
            topBar = topAppbar,
            bottomBar = {
                TuduBottomNavigation(router = router)
            }
        ) {
            content.invoke()
        }
    }


}