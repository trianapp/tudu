package app.trian.tudu.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
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
        modifier = modifier
            .background(MaterialTheme.colors.background)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = task.deadline?.formatDate("hh:mm") ?: "00:00",
            style=MaterialTheme.typography.body2.copy(
                color = MaterialTheme.colors.onBackground
            )
        )
        Spacer(modifier = modifier.width(16.dp))
        Column {
            Text(
                text = task.name,
                style = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.onBackground
                )
            )
            Text(
                text = "${ctx.getString(R.string.label_reminder)} ${if(task.reminder) ctx.getString(R.string.reminder_yes) else ctx.getString(R.string.reminder_no)}",
                style = MaterialTheme.typography.caption.copy(
                    color = MaterialTheme.colors.onBackground
                ),
                modifier = modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(MaterialTheme.colors.surface)
                    .padding(
                        vertical = 4.dp,
                        horizontal = 10.dp
                ),
                textAlign = TextAlign.Center
            )

        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun PreviewItemTaskCalendar(){
    TuduTheme {
        ItemTaskCalendar(
            task = Task()
        )
    }
}