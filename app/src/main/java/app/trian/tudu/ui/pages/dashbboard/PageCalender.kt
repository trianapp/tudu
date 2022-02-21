package app.trian.tudu.ui.pages.dashbboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import app.trian.tudu.common.daysOfWeekFromLocale
import app.trian.tudu.common.signOut
import app.trian.tudu.ui.component.CalendarViewCompose
import app.trian.tudu.ui.component.task.BottomSheetInputNewTask
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.UserViewModel
import compose.icons.Octicons
import compose.icons.octicons.ArrowLeft24
import compose.icons.octicons.ArrowRight24
import compose.icons.octicons.Calendar24
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
    val daysOfWeek = daysOfWeekFromLocale()
    val currentUser by userViewModel.currentUser.observeAsState()
    var gestureEnabled by remember {
        mutableStateOf(false)
    }
    var calendarWeekMode by remember {
        mutableStateOf(false)
    }

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
        enableDrawerGesture = gestureEnabled,
        onDrawerStateChanged = {
            gestureEnabled = when(it){
                DrawerValue.Closed -> false
                DrawerValue.Open -> true
            }
        },
        sheetContent={
                     BottomSheetInputNewTask()
        },
        topAppbar = {
            TopAppBar(
                title={
                    Column(
                        modifier = modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            text = "2022",
                            style = MaterialTheme.typography.subtitle1,
                            modifier = modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = "Februari",
                            style = MaterialTheme.typography.subtitle2,
                            modifier = modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )

                    }
                },
                actions = {
                    IconToggleButton(checked = false, onCheckedChange = {
                        calendarWeekMode = !calendarWeekMode
                    }) {
                        Icon(imageVector = Octicons.ArrowLeft24, contentDescription = "")
                    }
                    IconToggleButton(checked = false, onCheckedChange = {
                        calendarWeekMode = !calendarWeekMode
                    }) {
                        Icon(imageVector = Octicons.ArrowRight24, contentDescription = "")
                    }
                }
            )
        },
        onLogout = {
            userViewModel.signOut{
                signOut()
            }
        },
        modalBottomSheetState=modalBottomSheetState
    ) {
        CalendarViewCompose(
            legend = daysOfWeek
        )
    }

}

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewPageCalendar(){

    TuduTheme {
        PageCalender(
            router = rememberNavController())
    }
}