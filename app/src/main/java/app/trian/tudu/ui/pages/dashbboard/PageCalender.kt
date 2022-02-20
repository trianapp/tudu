package app.trian.tudu.ui.pages.dashbboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.R
import app.trian.tudu.common.Routes
import app.trian.tudu.common.signOut
import app.trian.tudu.ui.component.task.BottomSheetInputNewTask
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun PageCalender(
    modifier: Modifier=Modifier,
    router: NavHostController
){
    val scope = rememberCoroutineScope()
    val userViewModel = hiltViewModel<UserViewModel>()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = false
    )
    val currentUser by userViewModel.currentUser.observeAsState()

    fun signOut(){
        scope.launch(Dispatchers.Main) {
            router.signOut()
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        userViewModel.getCurrentUser()
    })

    BasePagesDashboard(

        router = router,
        currentUser=currentUser,
        sheetContent={
                     BottomSheetInputNewTask()
        },
        topAppbar = {
            TopAppBar {
                Text(
                    text = "Activity",
                    style = MaterialTheme.typography.subtitle2,
                    modifier = modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        },
        onLogout = {
            userViewModel.signOut{
                signOut()
            }
        },
        modalBottomSheetState=modalBottomSheetState
    ) {

        Column(
            modifier = modifier.fillMaxWidth().fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_page_calendar_empty),
                contentDescription = "")
            Text(
                text = "Under Construction",
                style= TextStyle(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Text(
                text = "We're working o it!",
                style= TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }

}

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewPageCalendar(){

    TuduTheme {
        PageCalender(router = rememberNavController())
    }
}