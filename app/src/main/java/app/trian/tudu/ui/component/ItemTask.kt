package app.trian.tudu.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.RadioButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.trian.tudu.data.local.Task
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
    onMark:()->Unit={},
    onDone:(done:Boolean)->Unit={},
    onDetail:(task:Task)->Unit={}
) {
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
                .background(MaterialTheme.colorScheme.surface)
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
                    Icon(imageVector = Octicons.Milestone16, contentDescription = "")
                }

                Column(

                ) {
                    Text(
                        text = task.name,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = "November 22,2020",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light
                        )
                    )
                }

            }
            RadioButton(
                selected  = task.done,
                onClick = {
                    onDone(!task.done)
                }
            )


        }
    }
}
@Composable
fun ItemTaskGrid(
    modifier: Modifier=Modifier,
    task:Task,
    index:Int=0,
    onMark:()->Unit={},
    onDone:(done:Boolean)->Unit={},
    onDetail:(task:Task)->Unit={}
){
    val ctx = LocalContext.current
    val currentWidth = ctx
        .resources
        .displayMetrics.widthPixels.dp /
            LocalDensity.current.density
    val paddingStart = if((index%2) ==0) 0.dp else 6.dp
    val paddingEnd = if((index%2) ==0) 6.dp else 0.dp
    Box(modifier = modifier
        .width(((currentWidth/2)))
        .padding(
            top = 6.dp,
            bottom = 6.dp,
            start = paddingStart,
            end = paddingEnd

        )
    ){
        Column(
            modifier=modifier
                .clip(RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomEnd = 10.dp,
                    bottomStart = 10.dp
                ))
                .background(MaterialTheme.colorScheme.surface)
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
                Icon(imageVector = Octicons.Milestone24, contentDescription = "")
                RadioButton(
                    selected = task.done,
                    onClick = {
                        onDone(!task.done)
                    }
                )
            }
            Column(
                modifier=modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = task.name,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = "November 22,2020",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light
                    )
                )
            }
        }
    }
}
@Preview
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
        created_at=0,
        updated_at=1
    )
    TuduTheme {
        ItemTaskRow(
            task = task
        )
    }
}

@Preview
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
        created_at=0,
        updated_at=1
    )
    TuduTheme {
        ItemTaskGrid(
            task = task
        )
    }
}