package app.trian.tudu.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import app.trian.tudu.data.local.Todo
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
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = todo.done,
                enabled = true,
                onClick = {
                    onDone(todo.apply { done = !todo.done })
                }
            )
            TextField(
                modifier=modifier.fillMaxWidth(fraction = 0.9f),
                value = todoName,
                onValueChange = {
                    todoName = it
                    scope.launch {
                        onChange(todo.apply { name = it })
                    }

                },
                maxLines=1,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = Color.Transparent,
                ),
                placeholder = {
                    Text("Input Sub Task")
                }
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
                contentDescription = ""
            )
        }

    }
}

@Preview
@Composable
fun PreviewItemTodo() {
    TuduTheme {
        ItemTodo(
            todo = Todo(
               name = "ini todo",
               done = false,
                task_id = "",
               created_at = 0,
               updated_at = 0
            )
        ){

        }
    }
}