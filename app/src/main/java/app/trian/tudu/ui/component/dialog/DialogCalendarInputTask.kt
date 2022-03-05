package app.trian.tudu.ui.component.dialog

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import app.trian.tudu.R
import app.trian.tudu.common.findActivity
import app.trian.tudu.common.showTimePicker
import app.trian.tudu.ui.theme.TuduTheme
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Input Task
 * author Trian Damai
 * created_at 28/02/22 - 16.22
 * site https://trian.app
 */

@Composable
fun DialogCalendarInputTask(
    show:Boolean=false,
    dismissable:Boolean = false,
    currentDateTime: LocalDateTime?=null,
    reminderOn:Boolean=false,
    onDismiss:()->Unit ={},
    onConfirm:(LocalDateTime:OffsetDateTime, reminderOn:Boolean)->Unit={
        _,_ ->
    }
) {
    if(show){
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = dismissable,
                dismissOnClickOutside = dismissable
            )
        ) {
            ScreenDialogCalendarInputTask(
                currentDateTime=currentDateTime,
                reminderOn=reminderOn,
                onCancel = onDismiss,
                onConfirm = onConfirm
            )
        }
    }

}


@SuppressLint("NewApi")
@Composable
fun ScreenDialogCalendarInputTask(
    modifier: Modifier=Modifier,
    currentDateTime:LocalDateTime?=null,
    reminderOn:Boolean=false,
    onCancel:()->Unit,
    onConfirm:(LocalDateTime:OffsetDateTime, reminderOn:Boolean)->Unit
) {
    val ctx = LocalContext.current
    val activity =  ctx.findActivity()

    var date by remember {
        mutableStateOf<LocalDate?>(LocalDate.now())
    }
    var time by remember {
        mutableStateOf<LocalTime?>(LocalTime.now())
    }
    var isReminderOn by remember {
        mutableStateOf(reminderOn)
    }
    LaunchedEffect(key1 = Unit, block = {
        date = currentDateTime?.toLocalDate()
        time = currentDateTime?.toLocalTime()
    })


    Column(
        modifier = modifier
            .wrapContentHeight()
            .background(MaterialTheme.colors.background)
    ) {
        SelectableCalendar()
        Spacer(modifier = modifier.height(20.dp))
        Row (
            modifier = modifier
                .clickable {
                    activity?.showTimePicker(
                        Pair(
                            time?.hour ?: 0,
                            time?.minute ?: 0
                        ),
                        onSelect = {
                            time = LocalTime.of(it.first, it.second)
                        },
                        onDismiss = {}
                    )
                }
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = stringResource(R.string.label_deadline_time))
            Text(text = time?.format(DateTimeFormatter.ISO_TIME) ?: "00:00")

        }
        Spacer(modifier = modifier.height(20.dp))
        Row (
            modifier = modifier

                .clickable {
                    isReminderOn = !isReminderOn
                }
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = stringResource(id = R.string.label_reminder))
            Text(text = if(isReminderOn) ctx.getString(R.string.reminder_yes) else ctx.getString(R.string.reminder_no))

        }
        Row(
            modifier=modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = {onCancel()}) {
                Text(
                    text = stringResource(id = R.string.btn_cancel),
                    style=MaterialTheme.typography.button.copy(
                        color = MaterialTheme.colors.primary.copy(alpha = 0.5f)
                    )
                )
            }
            TextButton(onClick = {
                onConfirm(
                    OffsetDateTime.of(date,time, ZoneOffset.UTC),
                    isReminderOn
                )
            }) {
                Text(
                    text = stringResource(id = R.string.btn_save),
                    style = MaterialTheme.typography.button.copy(
                        color=MaterialTheme.colors.primary
                    )
                )
            }
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
fun PreviewScreenDialogCalendarInputTask() {
    TuduTheme {
        ScreenDialogCalendarInputTask(
            onCancel = {},
            onConfirm = {_,_->},
        )
    }
}