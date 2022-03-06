package app.trian.tudu.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@SuppressLint("NewApi")
@Composable
fun ItemCalendar(
    modifier:Modifier=Modifier,
    day:LocalDate,
    selectedDate:LocalDate?=null,
    onDayClicked:(date:LocalDate)->Unit

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

                .clip(CircleShape)
                .background(
                    when (day) {
                        today -> MaterialTheme.colors.primary
                        selectedDate -> MaterialTheme.colors.primaryVariant
                        else -> MaterialTheme.colors.background
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
                style = MaterialTheme.typography.body2.copy(
                    color = when(day){
                        today -> MaterialTheme.colors.onPrimary
                        selectedDate ->MaterialTheme.colors.onPrimary
                        else -> MaterialTheme.colors.onBackground
                    }
                )
            )
        }
    }
}