package app.trian.tudu.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import app.trian.tudu.R
import app.trian.tudu.base.BaseMainApp
import app.trian.tudu.data.dateTime.DateFormat
import app.trian.tudu.data.dateTime.TimeFormat


@Composable
fun DialogDateFormat(
    show:Boolean,
    dateFormat:String= DateFormat.YYYYMMDD.value,
    onDismiss:()->Unit={},
    onConfirm:(timeFormat:DateFormat)->Unit={}
) {
    val listFormat = listOf(
        DateFormat.MMDDYYYY,
        DateFormat.DDMMYYYY,
    )
    if(show){
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true,
                securePolicy = SecureFlagPolicy.Inherit,
                usePlatformDefaultWidth = false
            )
        ) {
            ScreenDialogDateFormat(
                title= stringResource(R.string.title_dialog_date_format),
                items=listFormat,
                selected=dateFormat,
                onDismiss = onDismiss,
                onConfirm = onConfirm
            )
        }
    }

}

@Composable
fun DialogTimeFormat(
    show:Boolean,
    timeFormat:String=TimeFormat.TWENTY.value,
    onDismiss:()->Unit={},
    onConfirm:(timeFormat:TimeFormat)->Unit={}
) {
    val listFormat = listOf(
        TimeFormat.DEFAULT,
        TimeFormat.TWELVE,
        TimeFormat.TWENTY
    )
    if(show){
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true,
                securePolicy = SecureFlagPolicy.Inherit,
                usePlatformDefaultWidth = false
            )
        ) {
            ScreenDialogTimeFormat(
                title= stringResource(R.string.title_dialog_time_format),
                items=listFormat,
                selected=timeFormat,
                onDismiss = onDismiss,
                onConfirm = onConfirm
            )
        }
    }

}

@Composable
fun ScreenDialogDateFormat(
    modifier: Modifier=Modifier,
    title:String="",
    items:List<DateFormat> = emptyList(),
    selected:String="",
    onDismiss: () -> Unit,
    onConfirm: (dateFormat: DateFormat) -> Unit
) {
    var selectedItem by remember {
        mutableStateOf(items.find { it.value == selected } ?: DateFormat.DEFAULT)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(all = 20.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

        )
        Spacer(modifier = modifier.height(20.dp))
        items.forEach {
            ItemDateFormat(
                data=it,
                selected = it.value == selectedItem.value
            ){
                selectedItem = it
            }
        }
        Row(
            modifier=modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(id = R.string.btn_cancel),
                    style=MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    )
                )
            }
            TextButton(onClick = {
                onConfirm(selectedItem)
            }) {
                Text(
                    text = stringResource(id = R.string.btn_save),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color=MaterialTheme.colorScheme.primary
                    )
                )
            }
        }

    }
}

@Composable
fun ScreenDialogTimeFormat(
    modifier: Modifier=Modifier,
    title:String="",
    items:List<TimeFormat> = emptyList(),
    selected:String="",
    onDismiss: () -> Unit,
    onConfirm: (timeFormat: TimeFormat) -> Unit
) {
    var selectedItem by remember {
        mutableStateOf(items.find { it.value == selected } ?: TimeFormat.DEFAULT)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(all = 20.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

        )
        Spacer(modifier = modifier.height(20.dp))
        items.forEach {
            ItemTimeFormat(
                data=it,
                selected = it.value == selectedItem.value
            ){
                selectedItem = it
            }
        }
        Row(
            modifier=modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(id = R.string.btn_cancel),
                    style=MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    )
                )
            }
            TextButton(onClick = {
                onConfirm(selectedItem)
            }) {
                Text(
                    text = stringResource(id = R.string.btn_save),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color=MaterialTheme.colorScheme.primary
                    )
                )
            }
        }

    }
}

@Composable
fun ItemDateFormat(
    modifier:Modifier=Modifier,
    data:DateFormat,
    selected:Boolean,
    onClick:()->Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Text(
            text =data.text,
            style= MaterialTheme.typography.bodyMedium.copy(
                color=MaterialTheme.colorScheme.onBackground
            )
        )
    }
}

@Composable
fun ItemTimeFormat(
    modifier:Modifier=Modifier,
    data:TimeFormat,
    selected:Boolean,
    onClick:()->Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Text(
            text =data.text,
            style= MaterialTheme.typography.bodyMedium.copy(
                color=MaterialTheme.colorScheme.onBackground
            )
        )
    }
}

@Preview(
    uiMode=UI_MODE_NIGHT_YES
)
@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun PreviewDialogDateFormat() {
    BaseMainApp {
        ScreenDialogDateFormat(
            title="Date Format",
            items = listOf(
                DateFormat.DDMMYYYY,
                DateFormat.MMDDYYYY
            ),
            onConfirm = {},
            onDismiss = {}
        )
    }
}


