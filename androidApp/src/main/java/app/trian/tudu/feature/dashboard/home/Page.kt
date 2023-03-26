package app.trian.tudu.feature.dashboard.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ModalBottomSheetValue.Hidden
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
import app.trian.tudu.base.extensions.navigateSingleTop
import app.trian.tudu.components.AppbarHome
import app.trian.tudu.components.BottomSheetInputNewTask
import app.trian.tudu.components.DialogConfirmation
import app.trian.tudu.components.DialogPickCategory
import app.trian.tudu.components.ItemTaskRow
import app.trian.tudu.components.ScreenEmptyTask
import app.trian.tudu.components.TuduBottomNavigation
import app.trian.tudu.feature.category.Category
import app.trian.tudu.feature.detailTask.DetailTask
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.date_time.DateTimeDialog
import com.maxkeppeler.sheets.date_time.models.DateTimeConfig
import com.maxkeppeler.sheets.date_time.models.DateTimeSelection

object Home {
    const val routeName = "Home"
}

fun NavGraphBuilder.routeHome(
    state: ApplicationState,
) {
    composable(Home.routeName) { ScreenHome(appState = state) }
}

@Composable
internal fun ScreenHome(
    appState: ApplicationState,
) = UIWrapper<HomeViewModel>(appState = appState) {
    val state by uiState.collectAsState()
    val dataState by uiDataState.collectAsState()
    val ctx = LocalContext.current
    val datePickerUseCase = rememberUseCaseState()
    val timPickerUseCase = rememberUseCaseState()


    LaunchedEffect(key1 = this, block = {
        dispatch(HomeEvent.GetData)
    })

    with(appState) {
        setupTopAppBar {
            AppbarHome(
                dataCategory = dataState.categories,
                showDropdownOptionCategory = state.showDropdownMoreOption,
                onOptionMenuSelected = {
                    commit { copy(showDropdownMoreOption = false) }
                    when (it) {
                        R.string.option_category_management -> navigateSingleTop(Category.routeName)
                        R.string.option_search -> showSnackbar(R.string.text_message_coming_soon)
                        R.string.option_sort -> showSnackbar(R.string.text_message_coming_soon)
                    }
                },
                onSelectCategory = {
                    dispatch(HomeEvent.FilterTask(it.categoryId))
                },
                onShowDropDownOptionCategory = {
                    commit { copy(showDropdownMoreOption = true) }
                },
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
                    datePickerUseCase.show()
                },
                onAddTime = {
                    timPickerUseCase.show()
                },
                onAddTodo = {
                    dispatch(HomeEvent.AddPlainTodo(""))
                },
                onDeleteTodo = {
                    dispatch(HomeEvent.DeletePlainTodo(it.todoId))
                },
                onEditTodo = {
                    dispatch(HomeEvent.UpdatePlainTodo(it))
                },
                onSubmit = {
                    dispatch(HomeEvent.SubmitTask)
                    ctx.hideKeyboard()
                }
            )
        }
        setupBottomAppBar {
            TuduBottomNavigation(appState = appState)
        }
        addOnBottomSheetStateChangeListener {
            if (it == Hidden) {
                ctx.hideKeyboard()
            }
        }
    }

    DialogConfirmation(
        show = state.showDialogDeleteTask,
        message = stringResource(R.string.text_confirmation_delete, state.taskName),
        onConfirm = {
            dispatch(HomeEvent.DeleteTask)
        },
        onCancel = {
            commit { copy(showDialogDeleteTask = false) }
        }
    )
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
    DateTimeDialog(
        state = datePickerUseCase,
        selection = DateTimeSelection.Date {
            commit {
                copy(
                    dueDate = it,
                    hasDueDate = true
                )
            }
        },
        config = DateTimeConfig(

        )
    )
    DateTimeDialog(
        state = timPickerUseCase,
        selection = DateTimeSelection.Time {
            commit {
                copy(
                    dueTime = it,
                    hasDueTime = true
                )
            }
        }
    )
    Box(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxSize()
    ) {
        when (dataState.filteredTask.isEmpty()) {
            true -> {
                ScreenEmptyTask()
            }

            false -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                ) {

                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    items(dataState.filteredTask) { data ->
                        ItemTaskRow(
                            taskName = data.task.taskName,
                            taskDueDate = data.task.taskDueDate,
                            taskDueTime = data.task.taskDueTime,
                            taskNote = data.task.taskNote,
                            taskDone = data.task.taskDone,
                            categories = data.category.map { it.categoryName },
                            onDetail = {
                                if (data.task.taskId.isNotEmpty()) {
                                    navigateSingleTop(DetailTask.routeName, data.task.taskId)
                                }
                            },
                            onDelete = {
                                commit {
                                    copy(
                                        showDialogDeleteTask = true,
                                        taskId = data.task.taskId,
                                        taskName = data.task.taskName
                                    )
                                }
                            },
                            onDone = {
                                dispatch(
                                    HomeEvent.DoneTask(
                                        taskId = data.task.taskId,
                                        isDone = !data.task.taskDone
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            onClick = { showBottomSheet() },
            modifier = Modifier
                .align(
                    Alignment.BottomEnd
                )
                .padding(end = 16.dp),
            shape = MaterialTheme.shapes.medium,
        ) {
            Icon(
                imageVector = Outlined.Add, contentDescription = "", tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}


@Preview
@Composable
fun PreviewScreenHome() {
    BaseMainApp {
        ScreenHome(it)
    }
}