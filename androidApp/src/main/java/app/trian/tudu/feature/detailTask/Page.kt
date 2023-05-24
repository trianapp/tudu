/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */


package app.trian.tudu.feature.detailTask

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Note
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.trian.tudu.ApplicationState
import app.trian.tudu.R
import app.trian.tudu.base.BaseMainApp
import app.trian.tudu.base.UIWrapper
import app.trian.tudu.base.extensions.hideKeyboard
import app.trian.tudu.components.DialogConfirmation
import app.trian.tudu.components.DialogDatePicker
import app.trian.tudu.components.DialogPickCategory
import app.trian.tudu.components.DialogTimePicker
import app.trian.tudu.components.ItemTodo
import app.trian.tudu.feature.inputNote.InputNote
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode

object DetailTask {
    const val routeName = "DetailTask"
    const val keyArgs = "taskId"
    fun routeName() = buildString {
        append(routeName)
        append("/")
        append("{$keyArgs}")
    }

    val args = listOf(navArgument(keyArgs) {
        type = NavType.StringType
    })
}

fun NavGraphBuilder.routeDetailTask(
    state: ApplicationState,
) {
    composable(
        route = DetailTask.routeName(),
        arguments = DetailTask.args
    ) {
        ScreenDetailTask(appState = state)
    }
}

@Composable
internal fun ScreenDetailTask(
    appState: ApplicationState,
) = UIWrapper<DetailTaskViewModel>(appState = appState) {
    val ctx = LocalContext.current
    val state by uiState.collectAsState()
    val dataState by uiDataState.collectAsState()
    val lazyState = rememberLazyListState()
    val visibleTitle by remember {
        derivedStateOf { lazyState.firstVisibleItemIndex > 1 }
    }

    with(appState) {
        setupTopAppBar {
            TopAppBar(
                navigationIcon = {
                    IconToggleButton(
                        checked = false, onCheckedChange = {
                            ctx.hideKeyboard()
                            dispatch(DetailTaskEvent.CheckBackPressed)
                        }
                    ) {
                        Icon(
                            imageVector = Outlined.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Text(text = state.titleApp)
                }
            )
        }

        setupBottomAppBar {
            BottomAppBar(
                actions = {

                    IconButton(onClick = {
                        commit {
                            copy(
                                showDialogDeleteConfirmation = true,
                            )
                        }
                    }) {
                        Icon(
                            imageVector = Outlined.Delete,
                            contentDescription = "Delete"
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            dispatch(DetailTaskEvent.UpdateTaskDone(!state.taskDone))
                        },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = if (state.taskDone) "Undone" else "Done",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
            )
        }
    }
    if (visibleTitle) {
        commit { copy(titleApp = state.taskName) }
    } else {
        commit { copy(titleApp = "") }
    }
    BackHandler {
        dispatch(DetailTaskEvent.CheckBackPressed)
    }
    DialogConfirmation(
        show = state.showDialogDeleteConfirmation,
        message = stringResource(R.string.text_message_confirmation_delete_task),
        onCancel = {
            commit { copy(showDialogDeleteConfirmation = false) }
        },
        onConfirm = {
            dispatch(DetailTaskEvent.DeleteTask)
        }
    )
    DialogDatePicker(
        show = state.showDialogPickDueDate,
        currentSelectedDate = state.taskDueDate,
        onSubmit = {
            dispatch(DetailTaskEvent.UpdateTaskDueDate(it))
        },
        onDismiss = {
            commit { copy(showDialogPickDueDate = false) }
        }
    )

    DialogTimePicker(
        show = state.showDialogPickDueTime,
        currentSelectedTime = state.taskDueTime,
        onSubmit = {
            dispatch(DetailTaskEvent.UpdateTaskDueTime(it))
        },
        onDismiss = {
            commit { copy(showDialogPickDueTime = false) }
        }
    )
    DialogPickCategory(
        show = state.showDialogPickCategory,
        currentSelectedCategory = dataState.categories,
        categories = dataState.allCategories,
        onSubmit = {
            dispatch(DetailTaskEvent.UpdateTaskCategory(it))
        },
        onHide = {
            commit { copy(showDialogPickCategory = false) }
        }
    )

    LaunchedEffect(key1 = this, block = {
        dispatch(DetailTaskEvent.GetDetailTask)
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn(
            state = lazyState,
            content = {
                item {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.taskName,
                        onValueChange = {
                            dispatch(DetailTaskEvent.UpdateTaskName(it))
                        },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.placeholder_input_task),
                                style = MaterialTheme.typography.displaySmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.surfaceVariant
                            )
                        },
                        textStyle = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.primary,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        )
                    )
                }
                item {
                    ListItem(
                        headlineContent = {
                            Text(
                                text = stringResource(R.string.label_incomplete_todo),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        },
                        trailingContent = {
                            IconButton(
                                onClick = {
                                    dispatch(DetailTaskEvent.CreateTodo)
                                }
                            ) {
                                Icon(
                                    imageVector = Outlined.Add,
                                    contentDescription = "Add Sub Task",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    )
                }
                items(dataState.todos) { data ->
                    Box(
                        modifier = Modifier.padding(
                            horizontal = 20.dp
                        )
                    ) {
                        ItemTodo(
                            todoName = data.todoName,
                            todoDone = data.todoDone,
                            onDone = {
                                dispatch(
                                    DetailTaskEvent.UpdateTodoDone(
                                        todoId = data.todoId,
                                        isDone = !data.todoDone
                                    )
                                )
                            },
                            onChange = {
                                dispatch(
                                    DetailTaskEvent.UpdateTodoName(
                                        todoId = data.todoId,
                                        todoName = it,
                                    )
                                )
                            },
                            onDelete = {
                                dispatch(
                                    DetailTaskEvent.DeleteTodo(
                                        data.todoId
                                    )
                                )
                            }
                        )
                    }
                }
                if (dataState.todos.isEmpty()) {
                    item {
                        Row(
                            modifier = Modifier.padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.placeholder_no_todo),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onBackground
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
                item {
                    ListItem(
                        headlineContent = {
                            Text(
                                text = stringResource(R.string.label_detail_task_item),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )
                        }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    ListItem(
                        modifier = Modifier.clickable {
                            commit {
                                copy(
                                    showDialogPickDueDate = true
                                )
                            }
                        },
                        leadingContent = {
                            Icon(
                                imageVector = Outlined.CalendarMonth,
                                contentDescription = stringResource(R.string.content_description_icon_due_date),
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        },
                        headlineContent = {
                            Text(
                                text = stringResource(R.string.label_due_date),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.tertiary
                            )

                        },
                        supportingContent = {
                            Text(
                                text = state.taskDueDate.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    )
                }
                item {
                    ListItem(
                        modifier = Modifier.clickable {
                            commit {
                                copy(
                                    showDialogPickDueTime = true
                                )
                            }
                        },
                        leadingContent = {
                            Icon(
                                imageVector = Outlined.AccessTime,
                                contentDescription = stringResource(R.string.content_description_icon_due_date),
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        },
                        headlineContent = {
                            Text(
                                text = stringResource(R.string.label_due_time),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.tertiary
                            )

                        },
                        supportingContent = {
                            Text(
                                text = state.taskDueTime.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    )
                }
                item {
                    ListItem(
                        leadingContent = {
                            Icon(
                                imageVector = Outlined.AccessTime,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        },
                        headlineContent = {
                            Text(
                                text = stringResource(R.string.label_reminder),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.tertiary
                            )

                        },
                        supportingContent = {
                            Text(
                                text = if (state.taskReminder) stringResource(R.string.reminder_yes)
                                else stringResource(R.string.reminder_no),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        },
                        trailingContent = {
                            Checkbox(
                                checked = state.taskReminder,
                                onCheckedChange = {
                                    dispatch(DetailTaskEvent.UpdateTaskReminder(it))
                                }
                            )
                        }
                    )
                }
                item {
                    ListItem(
                        leadingContent = {
                            Icon(
                                imageVector = Outlined.Note,
                                contentDescription = "Note",
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        },
                        headlineContent = {
                            Text(
                                text = stringResource(R.string.label_note),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                            )

                        },
                        supportingContent = {
                            Text(
                                text = state.taskNote.ifEmpty { stringResource(R.string.text_no_note) },
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                            )
                        },
                        trailingContent = {
                            IconButton(
                                onClick = {
                                    navigateSingleTop(InputNote.routeName, state.taskId)
                                }
                            ) {
                                Icon(
                                    imageVector = Outlined.ChevronRight,
                                    contentDescription = "Edit Note",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    )
                }
                item {
                    Divider()
                    ListItem(
                        modifier = Modifier.clickable {
                            commit { copy(showDialogPickCategory = true) }
                        },
                        leadingContent = {
                            Icon(
                                imageVector = Outlined.Category,
                                contentDescription = "Category",
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        },
                        headlineContent = {
                            Text(
                                text = stringResource(R.string.label_category),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        },
                        supportingContent = {
                            FlowRow(
                                mainAxisSize = SizeMode.Wrap,
                                mainAxisAlignment = MainAxisAlignment.Start,
                                crossAxisAlignment = FlowCrossAxisAlignment.Start,
                                crossAxisSpacing = 6.dp
                            ) {
                                dataState.categories.forEach {
                                    Text(
                                        text = "#${it.categoryName}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.tertiary
                                    )

                                }
                            }
                        },
                    )
                }
            })


    }
}


@Preview
@Composable
fun PreviewScreenDetailTask() {
    BaseMainApp {
        ScreenDetailTask(it)
    }
}