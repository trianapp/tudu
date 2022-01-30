package app.trian.tudu.ui.pages.dashbboard

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import app.trian.tudu.ui.component.task.BottomSheetInputNewTask

@ExperimentalMaterialApi
@Composable
fun PageProfile(
    router: NavHostController
){
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = false,
    )
    BasePagesDashboard(
        router = router,
        sheetContent={
            BottomSheetInputNewTask()
        },
        modalBottomSheetState=modalBottomSheetState
    ) {

    }
}