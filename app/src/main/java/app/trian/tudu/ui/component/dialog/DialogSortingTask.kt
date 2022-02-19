package app.trian.tudu.ui.component.dialog

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import app.trian.tudu.R
import app.trian.tudu.ui.theme.Inactivebackground
import app.trian.tudu.ui.theme.TuduTheme

/**
 * Dialog sorting task
 * author Trian Damai
 * created_at 05/02/22 - 10.37
 * site https://trian.app
 */

@Composable
fun DialogSortingTask(
    modifier: Modifier=Modifier,
    show:Boolean,
    onDismiss:()->Unit={},
    onConfirm:(sort:SortTask)->Unit={}
) {
    if(show){
        Dialog(
            onDismissRequest =  onDismiss,
            properties = DialogProperties(

            )
        ) {
            ScreenDialogSorting(
                onDismiss = onDismiss,
                onConfirm = onConfirm
            )
        }
    }
}

@Composable
fun ScreenDialogSorting(
    modifier: Modifier=Modifier,
    onDismiss:()->Unit={},
    onConfirm:(sort:SortTask)->Unit={}
) {
    val listSortedItem = listOf(
        SortTask.DueDate,
        SortTask.TaskCreate,
        SortTask.AlphabeticalAscending,
        SortTask.AlphabeticalDescending
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface)
    ) {
        Column(
            modifier = modifier.padding(all = 16.dp)
        ) {
            Text(
                text = "Task sorted by",
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.onSurface
                )
            )

        }
        Spacer(modifier = modifier.height(14.dp))
        Row (
            modifier=modifier
                .fillMaxWidth()
                .background(Inactivebackground)
                .padding(vertical = 6.dp, horizontal = 16.dp),
        ){
            Text(
                text = "it will switch to manual sorting mode automatically after dragging tasks to reorder",
                style = MaterialTheme.typography.caption.copy(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.DarkGray
                )
            )
        }
        Column(
            modifier = modifier.padding(all = 16.dp)
        ) {
            listSortedItem.forEach {
                ItemDialogSortTask(
                    sortItem = it,
                    onClick = {
                        onConfirm(it)
                    }
                )
            }
        }
        Row (modifier = modifier.fillMaxWidth().padding(all = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
            TextButton(onClick = { onDismiss() }) {
                Text(
                    text = stringResource(id = R.string.btn_cancel),
                    style= MaterialTheme.typography.button.copy(
                        color = MaterialTheme.colors.primary.copy(alpha = 0.6f)
                    )
                )
            }
        }
    }
}

@Composable
fun ItemDialogSortTask(
    modifier: Modifier=Modifier,
    sortItem:SortTask,
    onClick:()->Unit={}
) {
    Row(modifier= modifier
        .fillMaxWidth()
        .background(MaterialTheme.colors.surface),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        RadioButton(
            selected = false,
            onClick = {
                      onClick()
            },
        colors = RadioButtonDefaults.colors(
            selectedColor = MaterialTheme.colors.primary,
            unselectedColor = MaterialTheme.colors.onBackground,
            disabledColor = Inactivebackground
        ))
        Text(
            text = sortItem.text,
            style= MaterialTheme.typography.body2.copy(
                color = MaterialTheme.colors.onSurface
            )
        )
    }
}


/**
 * Enum class for list sorting item
 * author Trian Damai
 * created_at 05/02/22 - 16.38
 * site https://trian.app
 */

enum class SortTask(val text:String){
    DueDate("Due Date & Time"),
    TaskCreate("Task Create Time"),
    AlphabeticalAscending("Alphabetical A-Z"),
    AlphabeticalDescending("Alphabetical Z-A")
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun PreviewDialogSortingTask() {
    TuduTheme {
        ScreenDialogSorting(onDismiss = {}, onConfirm = {})
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun PreviewItemDialogSortTask(){
    TuduTheme {
        ItemDialogSortTask(sortItem = SortTask.AlphabeticalDescending)
    }
}