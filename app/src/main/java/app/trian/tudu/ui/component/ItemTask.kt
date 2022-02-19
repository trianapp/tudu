package app.trian.tudu.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.trian.tudu.R
import app.trian.tudu.common.toReadableDate
import app.trian.tudu.data.local.Task
import app.trian.tudu.ui.component.dialog.DateTimeFormat
import app.trian.tudu.ui.theme.HexToJetpackColor
import app.trian.tudu.ui.theme.TuduTheme
import compose.icons.Octicons
import compose.icons.octicons.Milestone16
import compose.icons.octicons.Milestone24

/**
 * TaskItem
 * author Trian Damai
 * created_at 29/01/22 - 14.39
 * site https://trian.app
 */

@Composable
fun ItemTaskRow(
    modifier:Modifier=Modifier,
    task:Task,
    dateFormat:String=DateTimeFormat.DDMMYYYY.value,
    onMark:(task:Task)->Unit={},
    onDone:(task:Task)->Unit={},
    onDetail:(task:Task)->Unit={}
) {
    var isDone by remember {
        mutableStateOf(task.done)
    }
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(
            vertical = 4.dp,
        )
    ){
        Row(
            modifier= modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(HexToJetpackColor.getColor(task.secondColor))
                .clickable {
                    onDetail(task)
                }
                .padding(
                    top = 6.dp,
                    bottom = 6.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconToggleButton(checked = false, onCheckedChange = {}) {
                    Icon(
                        imageVector = Octicons.Milestone16,
                        contentDescription = stringResource(R.string.content_description_icon_label_task),
                        tint=MaterialTheme.colors.onBackground
                    )
                }

                Column(

                ) {
                    Text(
                        text = task.name,
                        maxLines=1,
                        modifier=modifier.fillMaxWidth(fraction = 0.8f),
                        overflow= TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.body2.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = HexToJetpackColor.getColor(task.color),
                            textDecoration = if(isDone) TextDecoration.LineThrough else TextDecoration.None
                        )
                    )
                    Text(
                        text = task.deadline.toReadableDate(dateFormat),
                        style = MaterialTheme.typography.subtitle2.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            color = HexToJetpackColor.getColor(task.color)
                        )
                    )
                }

            }
            RadioButton(
                selected  = isDone,
                onClick = {
                    isDone = !isDone
                    val taskUpdated = task.apply{
                        done = isDone
                    }
                    onDone(taskUpdated)
                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colors.primary,
                    unselectedColor = MaterialTheme.colors.secondary,
                    disabledColor = Color.DarkGray
                )
            )


        }
    }
}
@Composable
fun ItemTaskGrid(
    modifier: Modifier=Modifier,
    task:Task,
    index:Int=0,
    dateFormat:String=DateTimeFormat.DDMMYYYY.value,
    onMark:(task:Task)->Unit={},
    onDone:(task:Task)->Unit={},
    onDetail:(task:Task)->Unit={}
){
    val ctx = LocalContext.current
    val currentWidth = ctx
        .resources
        .displayMetrics.widthPixels.dp /
            LocalDensity.current.density
    val paddingStart = if((index%2) ==0) 0.dp else 6.dp
    val paddingEnd = if((index%2) ==0) 6.dp else 0.dp
    var isDone by remember {
        mutableStateOf(task.done)
    }
    Box(modifier = modifier
        .width(((currentWidth / 2)))
        .padding(
            top = 6.dp,
            bottom = 6.dp,
            start = paddingStart,
            end = paddingEnd

        )
    ){
        Column(
            modifier= modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp,
                        bottomEnd = 10.dp,
                        bottomStart = 10.dp
                    )
                )
                .background(HexToJetpackColor.getColor(task.secondColor))
                .clickable {
                    onDetail(task)
                }
                .padding(
                    all = 10.dp
                ),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier=modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Octicons.Milestone24,
                    contentDescription = stringResource(R.string.content_description_icon_label_task),
                    tint=MaterialTheme.colors.onBackground
                )
                RadioButton(
                    selected = isDone,
                    onClick = {
                        isDone = !isDone
                        val taskUpdated = task.apply{
                            done = isDone
                        }
                        onDone(taskUpdated)
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colors.primary,
                        unselectedColor = MaterialTheme.colors.secondary,
                        disabledColor = Color.DarkGray
                    )
                )
            }
            Column(
                modifier=modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = task.name,
                    maxLines=1,
                    overflow= TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body2.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = HexToJetpackColor.getColor(task.color),
                        textDecoration = if(isDone) TextDecoration.LineThrough else TextDecoration.None
                    )
                )
                Text(
                    text= task.deadline.toReadableDate(dateFormat),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        color = HexToJetpackColor.getColor(task.color)
                    )
                )
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
fun PreviewItemTaskRow(){
    val task = Task(
        taskId="iniasna",
        uid="iniuid",
        name="task pertama",
        deadline=0,
        done=false,
        done_at=0,
        note="ini",
        category_id="b",
        color = HexToJetpackColor.Red,
        secondColor = HexToJetpackColor.SecondRed,
        reminder = false,
        created_at=0,
        updated_at=1
    )
    TuduTheme {
        ItemTaskRow(
            task = task
        )
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewItemTaskGrid(){
    val task = Task(
        taskId="iniasna",
        uid="iniuid",
        name="task pertama",
        deadline=0,
        done=false,
        done_at=0,
        note="ini",
        category_id="b",
        color = HexToJetpackColor.Blue,
        secondColor = HexToJetpackColor.SecondBlue,
        reminder=false,
        created_at=0,
        updated_at=1
    )
    TuduTheme {
        ItemTaskGrid(
            task = task
        )
    }
}