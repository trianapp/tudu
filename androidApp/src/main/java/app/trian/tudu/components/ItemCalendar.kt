package app.trian.tudu.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.base.BaseMainApp
import java.time.LocalDate

@SuppressLint("NewApi")
@Composable
fun ItemCalendar(
    modifier: Modifier = Modifier,
    day: LocalDate,
    selectedDate: LocalDate? = null,
    onDayClicked: (date: LocalDate) -> Unit

) {
    val ctx = LocalContext.current
    val currentWidth = ctx
        .resources
        .displayMetrics.widthPixels.dp /
            LocalDensity.current.density
    val today = LocalDate.now()
    Box(
        modifier = modifier.size(currentWidth / 7)
    ) {
        Row(
            modifier = modifier
                .align(Alignment.Center)

                .clip(RoundedCornerShape(8.dp))
                .background(
                    when (day) {
                        today -> MaterialTheme.colorScheme.primary
                        selectedDate -> MaterialTheme.colorScheme.onSurface
                        else -> MaterialTheme.colorScheme.surface
                    }
                )
                .size(currentWidth / 9)
                .clickable {
                    onDayClicked(day)

                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = day.dayOfMonth.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = when (day) {
                        today -> MaterialTheme.colorScheme.surface
                        selectedDate -> MaterialTheme.colorScheme.surfaceVariant
                        else -> MaterialTheme.colorScheme.onSurface
                    }
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewItemCalendar() {
    BaseMainApp {
        LazyRow(content = {
            item {
                ItemCalendar(
                    day = LocalDate.now(),
                    selectedDate = LocalDate.now(),
                    onDayClicked = {}
                )
            }
            item {
                ItemCalendar(
                    day = LocalDate.now().plusDays(1),
                    selectedDate = LocalDate.now().plusDays(1),
                    onDayClicked = {}
                )
            }
        })
    }
}