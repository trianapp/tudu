package app.trian.tudu.ui.component

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.ui.theme.TuduTheme
import compose.icons.Octicons
import compose.icons.octicons.Plus16
import logcat.logcat

/**
 * Input new task
 * author Trian Damai
 * created_at 29/01/22 - 15.57
 * site https://trian.app
 */

@Composable
fun BottomSheetInputNewTask(
    modifier: Modifier=Modifier,
    onSubmit:(taskName:String)->Unit={},
    onPickCategory:()->Unit={},
    onAddTodo:()->Unit={},
    onHide:()->Unit={}
){
    val ctx = LocalContext.current

    var taskName by remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current

    fun submit(){
        if(taskName.isBlank()){
            Toast.makeText(ctx,"Task name cannot blank",Toast.LENGTH_LONG).show()
        }else{
            onSubmit(taskName)
            taskName = ""
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 16.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TextField(
                modifier=modifier.fillMaxWidth(),
                value = taskName,
                onValueChange = {
                    taskName=it
                },
                placeholder = {
                    Text(text = "Input new task")
                },
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                }
                )
            )
            Row(
                modifier=modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Category")
                IconToggleButton(
                    checked = false,
                    onCheckedChange = {
                        submit()
                    }
                ) {
                    Icon(imageVector = Octicons.Plus16, contentDescription = "")
                }
            }
        }

    }
}
@Preview
@Composable
fun PreviewBottomSheetInputNewTask(){
    TuduTheme {
        BottomSheetInputNewTask()
    }
}