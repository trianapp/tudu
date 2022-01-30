package app.trian.tudu.ui.component.task

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.data.local.Todo
import app.trian.tudu.ui.component.ItemAddTodo
import app.trian.tudu.ui.component.ItemTodo
import app.trian.tudu.ui.theme.TuduTheme
import compose.icons.Octicons
import compose.icons.octicons.*
import kotlinx.datetime.Clock
import logcat.logcat

/**
 * Input new task
 * author Trian Damai
 * created_at 29/01/22 - 15.57
 * site https://trian.app
 */

@SuppressLint("MutableCollectionMutableState")
@Composable
fun BottomSheetInputNewTask(
    modifier: Modifier=Modifier,
    onSubmit:(
        taskName:String,
        categoryId:String,
        todo:List<Todo>
    )->Unit={ _,_,_-> },
    onPickCategory:()->Unit={},
    onAddTodo:()->Unit={},
    onHide:()->Unit={}
){
    val ctx = LocalContext.current

    var taskName by remember {
        mutableStateOf(TextFieldValue(text = ""))
    }
    var categoryId by remember {
        mutableStateOf("")
    }
    var todos by remember {
        mutableStateOf<List<Todo>>(mutableListOf())
    }

    fun submit(){
        if(taskName.text.isBlank()){
            Toast.makeText(ctx,"Task name cannot blank",Toast.LENGTH_LONG).show()
        }else{
            onSubmit(
                taskName.text,
                categoryId,
                todos
            )
            taskName = TextFieldValue(text = "")
            categoryId = ""
            todos = mutableListOf()
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(
                RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp
                )
            )
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()

                .padding(
                    vertical = 16.dp,
                    horizontal = 16.dp
                )
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TextField(
                modifier=modifier.fillMaxWidth(),
                value =taskName,
                onValueChange = {
                    taskName = it
                },
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(text = "Input New Task Here")
                }

            )

            LazyColumn(content = {
                items(count=todos.size){
                    index: Int ->
                    ItemTodo(
                        todo = todos[index],
                        onDelete = {
                            todos = todos - todos[index]
                        },
                        onDone = {
                            todos[index].done = true
                        },
                        onChange = {
                            todos[index].name = it.name
                        }
                    )
                }
            })

            Row(
                modifier=modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box{
                    Text(text = "Category")
                }

                IconToggleButton(
                    checked = false,
                    onCheckedChange = {}
                ) {
                    Icon(
                        imageVector = Octicons.Calendar24,
                        contentDescription = ""
                    )
                }
                IconToggleButton(
                    checked = false,
                    onCheckedChange = {
                        val currentTime = Clock.System.now().toEpochMilliseconds()
                        todos = todos + Todo(
                            name = "",
                            done = false,
                            task_id = "",
                            created_at = currentTime,
                            updated_at = currentTime
                        )
                    }
                ) {
                    Icon(
                        imageVector = Octicons.GitMerge24,
                        contentDescription = ""
                    )
                }
                IconToggleButton(checked = false, onCheckedChange = {}) {
                    Icon(
                        imageVector = Octicons.Clock24,
                        contentDescription = ""
                    )
                }
                IconToggleButton(
                    checked = false,
                    onCheckedChange = {
                        submit()
                    }
                ) {
                    Icon(imageVector = Octicons.PaperAirplane24, contentDescription = "")
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
fun PreviewBottomSheetInputNewTask(){
    TuduTheme {
        BottomSheetInputNewTask()
    }
}