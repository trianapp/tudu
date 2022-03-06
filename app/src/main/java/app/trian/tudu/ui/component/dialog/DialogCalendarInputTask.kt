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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import app.trian.tudu.R
import app.trian.tudu.common.*
import app.trian.tudu.ui.component.ItemCalendar
import app.trian.tudu.ui.theme.TuduTheme
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import logcat.LogPriority
import logcat.logcat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Input Task
 * author Trian Damai
 * created_at 28/02/22 - 16.22
 * site https://trian.app
 */

@SuppressLint("NewApi")
@Composable
fun DialogCalendarInputTask(
    show:Boolean=false,
    dismissable:Boolean = false,
    date:LocalDate?=null,
    time:LocalTime?=null,
    reminderOn:Boolean=false,
    onDismiss:()->Unit ={},
    onConfirm:(date:LocalDate?, time:LocalTime?,reminderOn:Boolean)->Unit={
        _,_,_ ->
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
                date=date,
                time=time,
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
    date:LocalDate?=null,
    time:LocalTime?=null,
    reminderOn:Boolean=false,
    onCancel:()->Unit,
    onConfirm:(date:LocalDate?, time:LocalTime?,reminderOn:Boolean)->Unit
) {
    val ctx = LocalContext.current
    val currentWidth = ctx
        .resources
        .displayMetrics.widthPixels.dp /
            LocalDensity.current.density
    val activity =  ctx.findActivity()
    val today = LocalDate.now()

    var dateState by remember {
        mutableStateOf<LocalDate?>(LocalDate.now())
    }
    var timeState by remember {
        mutableStateOf<LocalTime?>(LocalTime.now())
    }
    var isReminderOn by remember {
        mutableStateOf(reminderOn)
    }
    LaunchedEffect(key1 = Unit, block = {
        dateState = date
        timeState = time
    })


    Column(
        modifier = modifier
            .wrapContentHeight()
            .padding(
                vertical = 16.dp,
                horizontal = 16.dp
            )
            .background(MaterialTheme.colors.background)
    ) {
        Spacer(modifier = modifier.height(20.dp))
        SelectableCalendar(
            modifier = modifier.size(currentWidth),
            showAdjacentMonths = false,
            dayContent = {
                day->
                ItemCalendar(
                    day = day.date,
                    selectedDate = dateState,
                    clickable =  true,
                    onDayClicked = {
                        logcat("tes",LogPriority.ERROR) { it.toReadableDate() }
                        logcat("tes",LogPriority.ERROR) { today.toReadableDate() }
                        logcat("tes",LogPriority.ERROR) { today.isBefore(it).toString() }
                        if(today.isBefore(it) || today.isEqual(it)) {
                            dateState = it
                        }else{
                            ctx.toastInfo(ctx.getString(R.string.toast_cannot_pick_lastday))
                        }
                    })
            }
        )
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
                            timeState = LocalTime.of(it.first, it.second)
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
                if(dateState==null && timeState == null){
                    onConfirm(
                        null,
                        null,
                        isReminderOn
                    )
                }else{
                    if(dateState == null || timeState == null){
                        ctx.toastError(ctx.getString(R.string.toast_validation_date_time))
                    }else {
                        onConfirm(
                            dateState,
                            timeState,
                            isReminderOn
                        )
                    }
                }

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
            onConfirm = {_,_,_->},
        )
    }
}