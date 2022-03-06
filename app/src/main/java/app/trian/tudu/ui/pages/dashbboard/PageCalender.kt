package app.trian.tudu.ui.pages.dashbboard

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.ui.res.stringResource
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
import app.trian.tudu.common.*
import app.trian.tudu.domain.ThemeData
import app.trian.tudu.ui.component.ItemCalendar
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
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.OffsetTime
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
        mutableStateOf<LocalDate?>(LocalDate.now())
    }

    LaunchedEffect(key1 = Unit, block = {
        userViewModel.getCurrentUser()
        taskViewModel.getListTaskByDate(OffsetDateTime.now())
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
        Column {
            SelectableCalendar(
                showAdjacentMonths = false,
                calendarState = calendarState,
                monthHeader = {},
                dayContent = {
                        day->
                   ItemCalendar(
                       day = day.date,
                       selectedDate = selectedDate,
                       onDayClicked = {
                       selectedDate =day.date
                       taskViewModel.getListTaskByDate(day.date.atTime(OffsetTime.MAX))
                   })
                }
            )
            if(listTask.isNotEmpty()){
                LazyColumn(content = {
                    items(listTask){
                            task->
                        ItemTaskCalendar(
                            task=task
                        )
                    }
                })
            }else{
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.calendar_no_task),
                        contentDescription =
                        stringResource(R.string.content_description_image_page_calendar_no_data,selectedDate.toReadableDate()))
                    Text(
                        text = stringResource(R.string.text_no_data_page_calendar,selectedDate.toReadableDate())
                    )
                }

            }
        }
    }

}

@ExperimentalMaterialApi
@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
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