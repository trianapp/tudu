package app.trian.tudu.ui.pages.dashbboard

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.navigation.NavHostController
import app.trian.tudu.common.Routes
import app.trian.tudu.ui.component.TuduBottomNavigation
import app.trian.tudu.ui.component.dialog.DialogLogout
import app.trian.tudu.ui.component.drawer.DrawerContent
import app.trian.tudu.ui.theme.ScrimColor
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import logcat.logcat

@SuppressLint("ServiceCast")
@ExperimentalMaterialApi
@Composable
fun BasePagesDashboard(
    router: NavHostController,
    modalBottomSheetState:ModalBottomSheetState,
    currentUser:FirebaseUser?,
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
                currentUser = currentUser,
                onClick = {
                    if(it.route == "logout"){
                        showDialogLogout=true
                    }
                },
                onNavigate = {
                    router.navigate(it)
                }
            )
        },
        drawerElevation = 0.dp,
        scrimColor = ScrimColor
    ) {
        ModalBottomSheetLayout(
            sheetState =modalBottomSheetState,
            sheetContent = {
                sheetContent.invoke()
            },
            scrimColor = ScrimColor,
            sheetBackgroundColor = Color.Transparent
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