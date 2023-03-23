package app.trian.tudu.feature.dashboard.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.trian.tudu.ApplicationState
import app.trian.tudu.R
import app.trian.tudu.base.BaseMainApp
import app.trian.tudu.base.UIWrapper
import app.trian.tudu.base.extensions.addOnBottomSheetStateChangeListener
import app.trian.tudu.base.extensions.hideKeyboard
import app.trian.tudu.base.extensions.toReadableDate
import app.trian.tudu.components.BottomSheetInputNewTask
import app.trian.tudu.components.DialogDatePicker
import app.trian.tudu.components.DialogPickCategory
import app.trian.tudu.components.DialogTimePicker
import app.trian.tudu.components.ItemCalendar
import app.trian.tudu.components.ItemTaskCalendar
import app.trian.tudu.components.TuduBottomNavigation
import io.github.boguszpawlowski.composecalendar.WeekCalendar
import io.github.boguszpawlowski.composecalendar.header.WeekState
import io.github.boguszpawlowski.composecalendar.rememberSelectableWeekCalendarState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode.Single
import io.github.boguszpawlowski.composecalendar.week.Week
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.format.TextStyle.SHORT
import java.util.Locale

object Calendar {
    const val routeName = "Calendar"
}

fun NavGraphBuilder.routeCalendar(
    state: ApplicationState,
) {
    composable(Calendar.routeName) {
        ScreenCalendar(appState = state)
    }
}

@Composable
internal fun ScreenCalendar(
    appState: ApplicationState,
) = UIWrapper<CalendarViewModel>(appState = appState) {
    val state by uiState.collectAsState()
    val dataState by uiDataState.collectAsState()
    val calendarState = rememberSelectableWeekCalendarState(
        weekState = WeekState(
            initialWeek = Week.now()
        ),
        initialSelectionMode = Single,
        confirmSelectionChange = {
            true
        }
    )


    val ctx = LocalContext.current
    val today = LocalDate.now()
    with(appState) {
        setupTopAppBar {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            text = calendarState.weekState.currentWeek.yearMonth.year.toString(),
                            style = MaterialTheme.typography.titleSmall.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = calendarState.weekState.currentWeek.yearMonth.month.getDisplayName(
                                TextStyle.FULL,
                                Locale.ENGLISH
                            ),
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )

                    }
                }
            )
        }
        setupBottomSheet {
            BottomSheetInputNewTask(
                taskName = state.taskName,
                todos = state.todos,
                categories = state.categories,
                hasCategory = state.hasCategory,
                hasDueDate = state.hasDueDate,
                hasReminder = state.hasDueTime,
                onChangeTaskName = {
                    commit { copy(taskName = it) }
                },
                onAddCategory = {
                    commit { copy(showDialogPickCategory = true) }
                },
                onAddDate = {
                    commit { copy(showDialogAddDate = true) }
                },
                onAddTime = {
                    commit { copy(showDialogAddTime = true) }
                },
                onAddTodo = {
                    dispatch(CalendarEvent.AddPlainTodo(""))
                },
                onDeleteTodo = {
                    dispatch(CalendarEvent.DeletePlainTodo(it.todoId))
                },
                onEditTodo = {
                    dispatch(CalendarEvent.UpdatePlainTodo(it))
                },
                onSubmit = {
                    dispatch(CalendarEvent.SubmitTask)
                    ctx.hideKeyboard()
                }
            )
        }
        setupBottomAppBar {
            TuduBottomNavigation(appState = appState)
        }
        addOnBottomSheetStateChangeListener {
            if (it == ModalBottomSheetValue.Hidden) {
                ctx.hideKeyboard()
            }
        }
    }

    DialogPickCategory(
        show = state.showDialogPickCategory,
        currentSelectedCategory = state.categories,
        categories = dataState.categories,
        onSubmit = {
            commit {
                copy(
                    categories = it.filter { it.categoryId != "all" },
                    showDialogPickCategory = false,
                    hasCategory = it.isNotEmpty()
                )
            }
        },
        onHide = {
            commit { copy(showDialogPickCategory = false) }
        }
    )
    DialogDatePicker(
        show = state.showDialogAddDate,
        onDismiss = {
            commit { copy(showDialogAddDate = false) }
        },
        onSubmit = {
            commit {
                copy(
                    showDialogAddDate = false,
                    dueDate = it,
                    hasDueDate = true
                )
            }
        }
    )
    DialogTimePicker(
        show = state.showDialogAddTime,
        onDismiss = {
            commit { copy(showDialogAddTime = false) }
        },
        onSubmit = {
            commit {
                copy(
                    showDialogAddTime = false,
                    dueTime = it,
                    hasDueTime = true
                )
            }
        }
    )
    LaunchedEffect(key1 = this, block = {
        dispatch(CalendarEvent.GetData)
    })
    Box(
        modifier = Modifier
            .padding(bottom = 8.dp, top = 16.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            WeekCalendar(
                horizontalSwipeEnabled = true,
                today = today,
                calendarState = calendarState,
                weekHeader = {},
                daysOfWeekHeader = {
                    Row {
                        it.forEach { dayOfWeek ->
                            Text(
                                textAlign = TextAlign.Center,
                                text = dayOfWeek.getDisplayName(SHORT, Locale.getDefault()),
                                modifier = Modifier
                                    .weight(1f)
                                    .wrapContentHeight(),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                dayContent = { day ->
                    ItemCalendar(
                        day = day.date,
                        selectedDate = state.selectedDate,
                        onDayClicked = {
                            calendarState.selectionState.onDateSelected(it)
                            dispatch(CalendarEvent.GetTask(it))
                        }
                    )
                }
            )
            when {
                dataState.task.isNotEmpty() -> {
                    LazyColumn(content = {
                        items(dataState.task) { task ->
                            ItemTaskCalendar(
                                taskName = task.task.taskName,
                                taskDuaDate = task.task.taskDueDate,
                                taskDueTime = task.task.taskDueTime
                            )
                        }
                    })
                }

                else -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.calendar_no_task),
                            contentDescription = stringResource(
                                R.string.content_description_image_page_calendar_no_data,
                                if (today == state.selectedDate) ctx.getString(R.string.text_today)
                                else state.selectedDate.toReadableDate()
                            )
                        )
                        Text(
                            text = stringResource(
                                R.string.text_no_data_page_calendar,
                                if (today == state.selectedDate) ctx.getString(R.string.text_today)
                                else state.selectedDate.toReadableDate()
                            )
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            onClick = {
                showBottomSheet()
            },
            modifier = Modifier
                .align(
                    Alignment.BottomEnd
                )
                .padding(end = 30.dp),
            shape = RoundedCornerShape(
                8.dp
            )
        ) {
            Icon(
                imageVector = Outlined.Add, contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }

}


@Preview
@Composable
fun PreviewScreenCalendar() {
    BaseMainApp {
        ScreenCalendar(it)
    }
}