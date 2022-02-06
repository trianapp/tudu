package app.trian.tudu.ui.component.dialog

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import app.trian.tudu.R
import app.trian.tudu.ui.theme.TuduTheme

@Composable
fun DialogDateFormat(
    modifier:Modifier=Modifier,
    show:Boolean,
    onDismiss:()->Unit={},
    onConfirm:(dateTimeFormat:DateTimeFormat)->Unit={}
) {
    val listFormat = listOf(
        DateTimeFormat.MMDDYYYY,
        DateTimeFormat.DDMMYYYY,
        DateTimeFormat.YYYYMMDD
    )
    if(show){
        Dialog(onDismissRequest = onDismiss) {
            ScreenDialogDateTimeFormat(
                title= stringResource(R.string.title_dialog_date_format),
                items=listFormat,
                onDismiss = onDismiss,
                onConfirm = onConfirm
            )
        }
    }

}

@Composable
fun DialogTimeFormat(
    modifier:Modifier=Modifier,
    show:Boolean,
    onDismiss:()->Unit={},
    onConfirm:(dateTimeFormat:DateTimeFormat)->Unit={}
) {
    val listFormat = listOf(
        DateTimeFormat.DEFAULT,
        DateTimeFormat.TWELVE,
        DateTimeFormat.TWENTY
    )
    if(show){
        Dialog(onDismissRequest = onDismiss) {
            ScreenDialogDateTimeFormat(
                title= stringResource(R.string.title_dialog_time_format),
                items=listFormat,
                onDismiss = onDismiss,
                onConfirm = onConfirm
            )
        }
    }

}

@Composable
fun ScreenDialogDateTimeFormat(
    modifier: Modifier=Modifier,
    title:String="",
    items:List<DateTimeFormat> = emptyList(),
    selected:String="",
    onDismiss: () -> Unit,
    onConfirm: (dateTimeFormat: DateTimeFormat) -> Unit
) {
    var selectedItem by remember {
        mutableStateOf(items.find { it.text == selected } ?: DateTimeFormat.DEFAULT)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.background)
            .padding(all = 20.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 20.sp,
                color = MaterialTheme.colors.onBackground
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
                    style=TextStyle(
                        color = MaterialTheme.colors.primary.copy(alpha = 0.5f)
                    )
                )
            }
            TextButton(onClick = {
                onConfirm(selectedItem)
            }) {
                Text(
                    text = stringResource(id = R.string.btn_save),
                    style = TextStyle(
                        color=MaterialTheme.colors.primary
                    )
                )
            }
        }

    }
}

@Composable
fun ItemDateFormat(
    modifier:Modifier=Modifier,
    data:DateTimeFormat,
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
            text = data.text,
            style= TextStyle(
                color=MaterialTheme.colors.onBackground
            )
        )
    }
}

enum class DateTimeFormat(var text:String,var value:String){
    YYYYMMDD("2022/02/06","YYYY/MM/DD"),
    DDMMYYYY("06/02/2022","DD/MM/YYYY"),
    MMDDYYYY("02/06/2022","MM/DD/YYYY"),
    DEFAULT("Default System","DEFAULT"),
    TWELVE("12 Hour","12"),
    TWENTY("24 Hour","24")
}

@Preview(
    uiMode=UI_MODE_NIGHT_YES
)
@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun PreviewDialogDateFormat() {
    TuduTheme {
        ScreenDialogDateTimeFormat(
            title="Date Format",
            items = listOf(
                DateTimeFormat.DDMMYYYY,
                DateTimeFormat.MMDDYYYY
            ),
            onConfirm = {},
            onDismiss = {}
        )
    }
}