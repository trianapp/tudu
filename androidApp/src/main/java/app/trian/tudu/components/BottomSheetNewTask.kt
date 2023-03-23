package app.trian.tudu.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessAlarm
import androidx.compose.material.icons.outlined.AddTask
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.R
import app.trian.tudu.base.BaseMainApp
import app.trian.tudu.base.extensions.hideKeyboard
import app.trian.tudu.data.model.CategoryModel
import app.trian.tudu.data.model.TodoModel
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment.Start
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode.Wrap

/**
 * Input new task
 * author Trian Damai
 * created_at 29/01/22 - 15.57
 * site https://trian.app
 */

@Composable
fun BottomSheetInputNewTask(
    todos: List<TodoModel> = listOf(),
    categories: List<CategoryModel> = listOf(),
    taskName: String = "",
    hasDueDate: Boolean = false,
    hasReminder: Boolean = false,
    hasCategory: Boolean = false,
    onChangeTaskName: (String) -> Unit = {},
    onAddCategory: () -> Unit = {},
    onAddDate: () -> Unit = {},
    onAddTime: () -> Unit = {},
    onAddTodo: () -> Unit = {},
    onDeleteTodo: (TodoModel) -> Unit = {},
    onEditTodo: (TodoModel) -> Unit = {},
    onSubmit: () -> Unit = { },
) {
    val ctx = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(
                RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp
                )
            )
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()

                .padding(
                    vertical = 16.dp,
                    horizontal = 16.dp
                )
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                mainAxisSize = Wrap,
                mainAxisAlignment = FlowMainAxisAlignment.Start,
                crossAxisSpacing = 6.dp,
                crossAxisAlignment = Start
            ) {
                categories.forEach {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .border(
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(
                                top = 1.dp,
                                bottom = 1.dp,
                                start = 8.dp,
                                end = 8.dp
                            )
                    ) {
                        Text(
                            text = "# ${it.categoryName}",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = taskName,
                onValueChange = onChangeTaskName,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = MaterialTheme.colorScheme.onSurface
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.placeholder_input_new_task),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                },
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        ctx.hideKeyboard()
                    }
                )

            )

            LazyColumn(content = {
                items(todos) { data ->
                    ItemTodo(
                        todoDone = data.todoDone,
                        todoName = data.todoName,
                        onDelete = { onDeleteTodo(data) },
                        onDone = {
                            onEditTodo(
                                data.copy(
                                    todoDone = !data.todoDone
                                )
                            )
                        },
                        onChange = {
                            onEditTodo(
                                data.copy(
                                    todoName = it
                                )
                            )
                        }
                    )
                }
            })

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconToggleButton(
                    checked = hasCategory,
                    onCheckedChange = {
                        onAddCategory()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Category,
                        contentDescription = "",
                        tint = if (hasCategory) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface
                    )
                }

                IconToggleButton(
                    checked = hasDueDate,
                    onCheckedChange = {
                        onAddDate()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth,
                        contentDescription = "",
                        tint = if (hasDueDate) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface
                    )
                }
                IconToggleButton(
                    checked = hasReminder,
                    onCheckedChange = {
                        onAddTime()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AccessAlarm,
                        contentDescription = "",
                        tint = if (hasReminder) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface
                    )
                }
                IconToggleButton(
                    checked = todos.isNotEmpty(),
                    onCheckedChange = {
                        onAddTodo()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AddTask,
                        contentDescription = "",
                        tint = if (todos.isNotEmpty()) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface
                    )
                }
                IconToggleButton(
                    checked = false,
                    onCheckedChange = {
                        onSubmit()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Send,
                        contentDescription = stringResource(R.string.content_description_icon_save_task),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewBottomSheetInputNewTask() {
    BaseMainApp {
        Column(

        ) {
            BottomSheetInputNewTask(
                categories = listOf(
                    CategoryModel(
                        categoryName = "WKWK",
                    ),
                    CategoryModel(
                        categoryName = "Kerjaan",
                    )
                )
            )
        }
    }
}
