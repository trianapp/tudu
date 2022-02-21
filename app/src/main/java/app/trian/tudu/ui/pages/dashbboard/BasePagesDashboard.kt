package app.trian.tudu.ui.pages.dashbboard

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

import androidx.navigation.NavHostController
import app.trian.tudu.R
import app.trian.tudu.common.Routes
import app.trian.tudu.common.emailTo
import app.trian.tudu.common.gotoApp
import app.trian.tudu.ui.component.TuduBottomNavigation
import app.trian.tudu.ui.component.dialog.DialogLogout
import app.trian.tudu.ui.component.drawer.DrawerContent
import app.trian.tudu.ui.theme.ScrimColor
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import logcat.LogPriority
import logcat.logcat

@SuppressLint("ServiceCast")
@ExperimentalMaterialApi
@Composable
fun BasePagesDashboard(
    router: NavHostController,
    modalBottomSheetState:ModalBottomSheetState,
    currentUser:FirebaseUser?,
    enableDrawerGesture:Boolean,
    onDrawerStateChanged:(DrawerValue)->Unit={},
    topAppbar:@Composable ()->Unit={},
    sheetContent:@Composable ()->Unit={},
    onLogout:()->Unit={},
    content:@Composable ()->Unit
) {
    val ctx = LocalContext.current
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed,
        confirmStateChange = {
            onDrawerStateChanged(it)
            true
        }
    )
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
                    when(it.route){
                        "logout" ->{
                            scope.launch {
                                drawerState.close()
                                onDrawerStateChanged(DrawerValue.Closed)
                                showDialogLogout=true
                            }
                        }
                        "send_feedback"->{
                            ctx.emailTo(
                                from = currentUser?.email ?: "",
                                to=ctx.getString(R.string.email_feedback),
                                subject =ctx.getString(R.string.subject_feedback)
                            )
                        }
                        "rating_app"->{
                            ctx.gotoApp()
                        }
                        "set_theme"->{

                        }
                    }
                },
                onNavigate = {
                    if(it.isNotBlank()) {
                        scope.launch {
                            drawerState.close()
                            onDrawerStateChanged(DrawerValue.Closed)
                            router.navigate(it)
                        }
                    }
                }
            )
        },
        drawerElevation = 0.dp,
        scrimColor = ScrimColor,
        gesturesEnabled = enableDrawerGesture
    ) {
        ModalBottomSheetLayout(
            sheetState = modalBottomSheetState,
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
                            scope.launch {
                                drawerState.open()
                                onDrawerStateChanged(DrawerValue.Open)
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