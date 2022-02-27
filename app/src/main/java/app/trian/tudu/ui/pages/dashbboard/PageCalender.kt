package app.trian.tudu.ui.pages.dashbboard

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.R
import app.trian.tudu.common.Routes
import app.trian.tudu.common.daysOfWeekFromLocale
import app.trian.tudu.common.getTheme
import app.trian.tudu.common.signOut
import app.trian.tudu.domain.ThemeData
import app.trian.tudu.ui.component.ItemTaskCalendar
import app.trian.tudu.ui.component.task.BottomSheetInputNewTask
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.TaskViewModel
import app.trian.tudu.viewmodel.UserViewModel
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.StaticCalendar
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import java.time.LocalDate
import java.util.*


@SuppressLint("NewApi")
@ExperimentalMaterialApi
@Composable
fun PageCalender(
    modifier: Modifier=Modifier,
    router: NavHostController,
    theme:String,
    onChangeTheme:(theme:String)->Unit,
    restartActivity:()->Unit
){

    val userViewModel = hiltViewModel<UserViewModel>()
    val taskViewModel = hiltViewModel<TaskViewModel>()
    val ctx = LocalContext.current
    val currentWidth = ctx
        .resources
        .displayMetrics.widthPixels.dp /
            LocalDensity.current.density
    val today = LocalDate.now()

    val listTask by taskViewModel.listTaskCalendar.observeAsState(initial = emptyList())


    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = false
    )
    val calendarState = rememberSelectableCalendarState(
        initialSelectionMode = SelectionMode.Single,
    )

    val currentUser by userViewModel.currentUser.observeAsState()
    var gestureEnabled by remember {
        mutableStateOf(false)
    }
    var selectedDate by remember {
        mutableStateOf<LocalDate?>(null)
    }



    LaunchedEffect(key1 = Unit, block = {
        userViewModel.getCurrentUser()
    })

    BasePagesDashboard(
        router = router,
        currentUser=currentUser,
        enableDrawerGesture = gestureEnabled,
        theme = theme,
        onChangeTheme = onChangeTheme,
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
                backgroundColor = MaterialTheme.colors.primary,
                title= {
                    Column(
                        modifier = modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            text = calendarState.monthState.currentMonth.year.toString(),
                            style = MaterialTheme.typography.subtitle1.copy(
                                color = MaterialTheme.colors.onPrimary
                            ),
                            modifier = modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = calendarState.monthState.currentMonth.month.getDisplayName(
                                java.time.format.TextStyle.FULL,
                                Locale.ENGLISH
                            ),
                            style = MaterialTheme.typography.h6.copy(
                                color = MaterialTheme.colors.onPrimary
                            ),
                            modifier = modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )

                    }
                }
            )
        },
        onLogout = {
            userViewModel.signOut{
                restartActivity()
            }
        },
        modalBottomSheetState=modalBottomSheetState
    ) {


        SelectableCalendar(
            showAdjacentMonths = false,
            calendarState = calendarState,
            monthHeader = {},
            dayContent = {
                day->
                Box(
                    modifier = modifier.size(currentWidth / 7)
                ) {
                    Row(
                        modifier = modifier
                            .align(Alignment.Center)

                            .clip(CircleShape)
                            .background(
                                when (day.date) {
                                    today -> MaterialTheme.colors.primary
                                    selectedDate -> MaterialTheme.colors.primaryVariant
                                    else -> MaterialTheme.colors.background
                                }
                            )
                            .size(currentWidth / 9)
                            .clickable {
                                       selectedDate =day.date

                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = day.date.dayOfMonth.toString(),
                            style = MaterialTheme.typography.body2.copy(
                                color = when(day.date){
                                    today -> MaterialTheme.colors.onPrimary
                                   selectedDate ->MaterialTheme.colors.onPrimary
                                    else -> MaterialTheme.colors.onBackground
                                }
                            )
                        )
                    }
                }
            }
        )
        LazyColumn(content = {
            items(listTask){
                task->
                ItemTaskCalendar()
            }
        })
    }

}

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewPageCalendar(){

    TuduTheme {
        PageCalender(
            router = rememberNavController(),
            theme = "",
            onChangeTheme = {},
            restartActivity = {}
        )
    }
}