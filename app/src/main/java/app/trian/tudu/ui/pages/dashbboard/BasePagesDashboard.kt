package app.trian.tudu.ui.pages.dashbboard

import android.annotation.SuppressLint
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import app.trian.tudu.ui.component.TuduBottomNavigation
import kotlinx.coroutines.launch

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
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalDrawer(
        drawerContent = {

        }
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
                    TuduBottomNavigation(
                        router = router,
                        onButton = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    )
                }
            ) {
                content.invoke()
            }
        }
    }



}