package app.trian.tudu.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.base.BaseMainApp

/**
 * Item Task Calendar
 * author Trian Damai
 * created_at 26/02/22 - 21.58
 * site https://trian.app
 */

@Composable
fun ItemTaskCalendar(
    taskName: String = "",
    taskDuaDate: String = "",
    taskDueTime: String = ""
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = taskDueTime.ifEmpty { "N/A" },
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(
                fraction = 0.2f
            )
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(
                    vertical = 8.dp,
                    horizontal = 10.dp
                )
        ) {
            Text(
                text = taskName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Start,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .border(
                        shape = MaterialTheme.shapes.small,
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                    .padding(
                        horizontal = 4.dp,
                        vertical = 2.dp
                    ),
                text = taskDuaDate,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                textAlign = TextAlign.Start
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
fun PreviewItemTaskCalendar() {
    BaseMainApp {
        LazyColumn(content = {
            item {
                ItemTaskCalendar(
                    taskName = "Rencana Mudik Hahah",
                    taskDuaDate = "2023-03-26",
                    taskDueTime = "08:00"
                )
            }
            item {
                ItemTaskCalendar(
                    taskName = "Rencana Mudik Hahah2",
                    taskDuaDate = "2023-03-26",
                    taskDueTime = "08:00"
                )
            }
        })
    }
}