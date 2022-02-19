package app.trian.tudu.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.R
import app.trian.tudu.data.local.Todo
import app.trian.tudu.ui.theme.Inactivebackground
import app.trian.tudu.ui.theme.TuduTheme
import compose.icons.Octicons
import compose.icons.octicons.X16
import kotlinx.coroutines.launch
import logcat.logcat

/**
 * Item todo
 * author Trian Damai
 * created_at 29/01/22 - 22.27
 * site https://trian.app
 */
@Composable
fun ItemTodo(
    modifier:Modifier=Modifier,
    todo: Todo,
    onChange:(todo:Todo)->Unit={},
    onDelete:(todo:Todo)->Unit={},
    onDone:(todo:Todo)->Unit={}
) {
    var todoName by remember {
        mutableStateOf(todo.name)
    }
    val scope = rememberCoroutineScope()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colors.background
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
        ) {
            RadioButton(
                selected = todo.done,
                enabled = true,
                onClick = {
                    onDone(todo.apply { done = !todo.done })
                },
                modifier = modifier
                    .align(Alignment.CenterStart),
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colors.primary,
                    unselectedColor = MaterialTheme.colors.onBackground,
                    disabledColor = Inactivebackground
                )

            )
            TextField(
                modifier= modifier
                    .fillMaxWidth(fraction = 0.9f)
                    .align(Alignment.CenterStart)
                    .padding(start = 30.dp),
                value = todoName,
                enabled = !todo.done,
                onValueChange = {
                    todoName = it
                    scope.launch {
                        onChange(todo.apply { name = it })
                    }

                },
                maxLines=1,
                singleLine=true,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        stringResource(id = R.string.placeholder_input_todo),
                        style= MaterialTheme.typography.caption.copy(
                            textDecoration = if(todo.done) TextDecoration.LineThrough else TextDecoration.None,
                            color = MaterialTheme.colors.onBackground
                        )
                    )
                },
                textStyle = MaterialTheme.typography.caption.copy(
                    textDecoration = if(todo.done) TextDecoration.LineThrough else TextDecoration.None,
                    color = MaterialTheme.colors.onBackground
                )
            )
        }
        IconToggleButton(
            checked = false,
            onCheckedChange = {
                onDelete(todo)
            }
        ) {
            Icon(
                imageVector = Octicons.X16,
                contentDescription = stringResource(R.string.content_description_icon_close),
                tint = MaterialTheme.colors.onBackground
            )
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
fun PreviewItemTodo() {
    TuduTheme {
        ItemTodo(
            todo = Todo(
               name = "ini todo",
               done = true,
                task_id = "",
               created_at = 0,
               updated_at = 0
            )
        ){

        }
    }
}