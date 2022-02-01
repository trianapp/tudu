package app.trian.tudu.ui.pages.dashbboard

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.navigation.NavHostController
import app.trian.tudu.common.Routes
import app.trian.tudu.ui.component.TuduBottomNavigation
import app.trian.tudu.ui.component.dialog.DialogLogout
import app.trian.tudu.ui.component.drawer.DrawerContent
import kotlinx.coroutines.delay
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
    onLogout:()->Unit={},
    content:@Composable ()->Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showDialogLogout by remember {
        mutableStateOf(false)
    }
    DialogLogout(
        show = showDialogLogout,
        onConfirm = {
            showDialogLogout = false
            onLogout()
        },
        onDismiss = {
            showDialogLogout=false
        },
        onCancel = {
            showDialogLogout=false
        }
    )

    ModalDrawer(
        drawerState=drawerState,
        drawerContent = {
            DrawerContent(
                onClick = {
                    if(it.route == "logout"){
                        showDialogLogout=true
                    }
                },
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