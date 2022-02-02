package app.trian.tudu.ui.pages.dashbboard

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import app.trian.tudu.common.Routes
import app.trian.tudu.common.signOut
import app.trian.tudu.ui.component.task.BottomSheetInputNewTask
import app.trian.tudu.viewmodel.UserViewModel

@ExperimentalMaterialApi
@Composable
fun PageCalender(
    router: NavHostController
){
    val userViewModel = hiltViewModel<UserViewModel>()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = false
    )
    val currentUser by userViewModel.currentUser.observeAsState()

    LaunchedEffect(key1 = Unit, block = {
        userViewModel.getCurrentUser()
    })

    BasePagesDashboard(
        router = router,
        currentUser=currentUser,
        sheetContent={
                     BottomSheetInputNewTask()
        },
        onLogout = {
            userViewModel.signOut{
                router.signOut()
            }
        },
        modalBottomSheetState=modalBottomSheetState
    ) {

    }

}