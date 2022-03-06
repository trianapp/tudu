package app.trian.tudu.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.R
import app.trian.tudu.common.formatDate
import app.trian.tudu.data.local.Task
import app.trian.tudu.ui.theme.TuduTheme

/**
 * Item Task Calendar
 * author Trian Damai
 * created_at 26/02/22 - 21.58
 * site https://trian.app
 */

@Composable
fun ItemTaskCalendar(
    modifier: Modifier=Modifier,
    task: Task
) {
    val ctx = LocalContext.current
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Text(text = task.deadline?.formatDate("hh:mm") ?: "00:00")
        Column {
            Text(text = task.name)
            Text(text = if(task.reminder) ctx.getString(R.string.reminder_yes) else ctx.getString(R.string.reminder_no))

        }
    }
}

@Preview
@Composable
fun PreviewItemTaskCalendar(){
    TuduTheme {
        ItemTaskCalendar(
            task = Task()
        )
    }
}