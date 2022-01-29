package app.trian.tudu.ui.pages.dashbboard

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.hardware.input.InputManager
import android.view.inputmethod.InputMethodManager
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.navigation.NavHostController
import app.trian.tudu.ui.component.BottomSheetInputNewTask
import app.trian.tudu.ui.component.TuduBottomNavigation
import kotlinx.coroutines.launch

@SuppressLint("ServiceCast")
@ExperimentalMaterialApi
@Composable
fun BasePagesDashboard(
    router: NavHostController,
    modalBottomSheetState:ModalBottomSheetState= rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
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
            bottomBar = {
                TuduBottomNavigation(router = router)
            }
        ) {
            content.invoke()
        }
    }


}