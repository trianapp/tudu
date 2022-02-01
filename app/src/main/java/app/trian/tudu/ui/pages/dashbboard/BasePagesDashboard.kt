package app.trian.tudu.ui.pages.dashbboard

import android.annotation.SuppressLint
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import app.trian.tudu.ui.component.TuduBottomNavigation
import app.trian.tudu.ui.component.drawer.DrawerContent
import kotlinx.coroutines.launch
import logcat.logcat

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
        drawerState=drawerState,
        drawerContent = {
            DrawerContent(
                onClick = {},
                onNavigate = {
                    router.navigate(it)
                }
            )
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
                            logcat("tes aja") {
                                "ini button"
                            }
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