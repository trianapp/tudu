package app.trian.tudu.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.data.local.Task
import app.trian.tudu.ui.theme.TuduTheme
import compose.icons.Octicons
import compose.icons.octicons.Bookmark16

/**
 * TaskItem
 * author Trian Damai
 * created_at 29/01/22 - 14.39
 * site https://trian.app
 */

@Composable
fun ItemTask(
    modifier:Modifier=Modifier,
    task:Task,
    onMark:()->Unit={},
    onDone:(done:Boolean)->Unit={},
    onDetail:(task:Task)->Unit={}
) {
    var done by remember {
        mutableStateOf(task.done)
    }
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(
            vertical = 4.dp,
            horizontal = 16.dp)
    ){
        Row(
            modifier=modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    onDetail(task)
                }
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = done,
                    onCheckedChange = {
                        done = it
                        onDone(it)
                    }
                )
                Text(text = task.name)
            }
            IconToggleButton(checked = false, onCheckedChange = {}) {
                Icon(imageVector = Octicons.Bookmark16, contentDescription = "")
            }

        }
    }
}
@Preview
@Composable
fun PreviewItemTask(){
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
        ItemTask(
            task = task
        )
    }
}