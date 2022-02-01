package app.trian.tudu.ui.component.task

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import app.trian.tudu.data.local.Task
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Screeninput note
 * author Trian Damai
 * created_at 01/02/22 - 19.39
 * site https://trian.app
 */
@Composable
fun ScreenInputNote(
    modifier:Modifier=Modifier,
    task: Task,
    onEdit:(task:Task)->Unit={}
) {
    val scope = rememberCoroutineScope()
    var noteState by remember {
        mutableStateOf(TextFieldValue(text = task.note))
    }
    fun update(){
        scope.launch {
            delay(500)
            onEdit(
                task.apply { note = noteState.text }
            )
        }

    }
    LazyColumn(content = {
        item {
            TextField(
                modifier=modifier.fillMaxWidth(),
                value = noteState,
                onValueChange = {
                    noteState = it
                    update()
                },
                placeholder={
                    Text(
                        text = noteState.text,
                        style = TextStyle(
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                },
                textStyle = TextStyle(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = Color.Black,
                    backgroundColor = Color.Transparent,
                )
            )
        }
    })
}