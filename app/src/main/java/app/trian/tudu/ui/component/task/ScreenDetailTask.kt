package app.trian.tudu.ui.component.task

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.trian.tudu.R
import app.trian.tudu.common.Routes
import app.trian.tudu.common.toReadableDate
import app.trian.tudu.data.local.Category
import app.trian.tudu.data.local.Task
import app.trian.tudu.data.local.Todo
import app.trian.tudu.ui.component.ItemAddTodo
import app.trian.tudu.ui.component.ItemTodo
import app.trian.tudu.ui.theme.HexToJetpackColor
import app.trian.tudu.ui.theme.InactiveText
import app.trian.tudu.ui.theme.Inactivebackground
import app.trian.tudu.ui.theme.TuduTheme
import compose.icons.Octicons
import compose.icons.octicons.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.joda.time.DateTime

/**
 * Screen Detail Task
 * author Trian Damai
 * created_at 30/01/22 - 22.31
 * site https://trian.app
 */
@Composable
fun ScreenDetailTask(
    modifier: Modifier=Modifier,
    task: Task,
    category: Category?,
    completeTodo:List<Todo> = emptyList(),
    unCompleteTodo:List<Todo> = emptyList(),
    updateTask:(task:Task)->Unit={},
    addNewTodo:()->Unit={},
    updateTodo:(todo:Todo)->Unit={},
    deleteTodo:(todo:Todo)->Unit={},
    onEditNote:(route:String)->Unit={}
) {
    val scope = rememberCoroutineScope()
    val ctx = LocalContext.current
    var taskName by remember {
        mutableStateOf(TextFieldValue(text = task.name))
    }
    var deadlineState by remember {
        mutableStateOf(task.deadline)
    }
    var reminderState by remember {
        mutableStateOf(task.reminder)
    }
    val date = DateTime(deadlineState)

    fun update(){
        scope.launch {
            delay(800)
            updateTask(
                task.apply {
                    name=taskName.text
                    deadline = deadlineState
                    done = reminderState

                }
            )
        }
    }
    fun getMonth(month:Int):Int{
        if(month <1){
            return month
        }
        return month -1
    }
    val datePickerDialog = DatePickerDialog(ctx,{
            _: DatePicker, year:Int, month:Int, day:Int->
        val date = DateTime(year,(month+1),day,0,0)
        deadlineState = date.millis
        update()
    },date.year,getMonth(date.monthOfYear),date.dayOfMonth)



    Column(
            modifier=modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Box(modifier = modifier.padding(horizontal = 20.dp)) {
                Row(
                    modifier = modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                                bottomStart = 10.dp,
                                bottomEnd = 10.dp
                            )
                        )
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(
                            vertical = 10.dp,
                            horizontal = 16.dp
                        )
                        .clickable { }
                ) {
                    Text(
                        text = category?.name ?: stringResource(id = R.string.no_category),
                        style= TextStyle(
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                    Icon(
                        imageVector = Octicons.ChevronDown16,
                        contentDescription = ""
                    )
                }
            }
            LazyColumn(content = {
                item {

                    Spacer(modifier = modifier.height(16.dp))
                    TextField(
                        modifier=modifier.fillMaxWidth(),
                        value = taskName,
                        onValueChange = {
                            taskName = it
                            update()
                        },
                        placeholder={
                            Text(
                                text = taskName.text.ifBlank { stringResource(R.string.placeholder_input_task) },
                                style = TextStyle(
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = InactiveText
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
                item {
                    Box (
                        modifier=modifier.padding(
                            horizontal = 20.dp
                        )
                    ){
                        Spacer(modifier = modifier.height(10.dp))
                        Text(
                            text = stringResource(R.string.label_incomplete_todo),
                            style= TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
                items(unCompleteTodo){
                        data->
                    Box (
                        modifier=modifier.padding(
                            horizontal = 20.dp
                        )
                    ) {
                        ItemTodo(
                            todo = data,
                            onDone = {
                                updateTodo(it)
                            },
                            onChange = {
                                updateTodo(it)
                            },
                            onDelete = {
                                deleteTodo(it)
                            }
                        )
                    }
                }
                item {
                    Box (
                        modifier=modifier.padding(
                            horizontal = 20.dp
                        )
                    ) {
                        ItemAddTodo {
                            addNewTodo()
                        }
                    }
                }
                item {
                    Box (
                        modifier=modifier.padding(
                            horizontal = 20.dp
                        )
                    ) {
                        Spacer(modifier = modifier.height(10.dp))
                        Text(
                            text = stringResource(R.string.label_completed_todo),
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Spacer(modifier = modifier.height(10.dp))
                    }
                }
                items(completeTodo){
                        data->
                    Box (
                        modifier=modifier.padding(
                            horizontal = 20.dp
                        )
                    ) {
                        ItemTodo(
                            todo = data,
                            onDone = {
                                updateTodo(it)
                            },
                            onChange = {
                                updateTodo(it)
                            },
                            onDelete = {
                                deleteTodo(it)
                            }
                        )
                    }
                }
                item {
                    Spacer(modifier = modifier.height(16.dp))
                    Divider()
                    Spacer(modifier = modifier.height(16.dp))
                    Box (
                        modifier=modifier.padding(
                            horizontal = 20.dp
                        )
                    ) {
                        Row(
                            modifier=modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row {
                                Icon(imageVector = Octicons.Calendar16, contentDescription = "")
                                Spacer(modifier = modifier.width(6.dp))
                                Text(text = stringResource(R.string.label_due_date))
                            }
                            Row(
                                modifier= modifier
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 10.dp,
                                            topEnd = 10.dp,
                                            bottomStart = 10.dp,
                                            bottomEnd = 10.dp
                                        )
                                    )
                                    .background(Inactivebackground)
                                    .padding(
                                        horizontal = 10.dp,
                                        vertical = 2.dp
                                    )
                                    .clickable {
                                        datePickerDialog.show()
                                    }
                            ) {
                                Text(text = deadlineState.toReadableDate())
                            }
                        }
                    }
                    Spacer(modifier = modifier.height(16.dp))
                }
                item {
                    Divider()
                    Spacer(modifier = modifier.height(16.dp))
                    Box (
                        modifier=modifier.padding(
                            horizontal = 20.dp
                        )
                    ) {
                        Row(
                            modifier=modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row {
                                Icon(imageVector = Octicons.Clock16, contentDescription = "")
                                Spacer(modifier = modifier.width(6.dp))
                                Text(text = stringResource(R.string.label_reminder))
                            }
                            Row(
                                modifier= modifier
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 10.dp,
                                            topEnd = 10.dp,
                                            bottomStart = 10.dp,
                                            bottomEnd = 10.dp
                                        )
                                    )
                                    .background(Inactivebackground)
                                    .padding(
                                        horizontal = 10.dp,
                                        vertical = 2.dp
                                    )
                                    .clickable {
                                        reminderState=!reminderState
                                        update()
                                    }
                            ) {
                                Text(text = if(reminderState) stringResource(R.string.reminder_yes)
                                else stringResource(R.string.reminder_no))
                            }
                        }
                    }
                    Spacer(modifier = modifier.height(16.dp))
                }
                item {
                    Divider()
                    Box (
                        modifier=modifier
                            .clickable {
                                onEditNote("${Routes.ADD_NOTE}/${task.taskId}")
                            }
                            .padding(
                                horizontal = 20.dp,
                                vertical = 16.dp
                            )
                    ) {
                        Row(
                            modifier=modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(imageVector = Octicons.Note16, contentDescription = "")
                                Spacer(modifier = modifier.width(6.dp))
                                Column(
                                    modifier=modifier.fillMaxWidth(fraction = 0.8f)
                                ) {
                                    Text(text = stringResource(R.string.label_note))
                                    Text(text = task.note)
                                }
                            }
                            Row(
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.Top,
                                modifier= modifier
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 10.dp,
                                            topEnd = 10.dp,
                                            bottomStart = 10.dp,
                                            bottomEnd = 10.dp
                                        )
                                    )
                                    .background(Inactivebackground)
                                    .padding(
                                        horizontal = 10.dp,
                                        vertical = 2.dp
                                    )
                            ) {
                                Text(text = stringResource(R.string.btn_add_note))
                            }
                        }
                    }
                    Divider()
                }
            })
        }

}

@Preview
@Composable
fun PreviewScreenDetailTask() {
    TuduTheme {
        ScreenDetailTask(
            task = Task(
                name = "Ini tuh task tau",
                note = "",
                done = false,
                deadline = 0,
                done_at = 0,
                category_id="",
                reminder=false,
                color = HexToJetpackColor.SecondBlue,
                secondColor = HexToJetpackColor.SecondBlue,
                created_at = 0,
                updated_at = 0
            ),
            category = Category(
                name = "No Category",
                created_at = 0,
                updated_at = 0,
                color = HexToJetpackColor.Blue
            )
        )
    }
}