package app.trian.tudu.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
            .background(MaterialTheme.colors.background)
            .padding(all = 16.dp)
    ) {
        Column {
            Text(
                text = "Task sorted by",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                text = "it will switch to manual sorting mode automatically after dragging tasks to reorder",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light
                )
            )
        }
        Column {
            listSortedItem.forEach {
                ItemDialogSortTask(sortItem = it)
            }
        }
        Row (modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Cancel")
            }
        }
    }
}

@Composable
fun ItemDialogSortTask(
    modifier: Modifier=Modifier,
    sortItem:SortTask
) {
    Row(modifier=modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        RadioButton(
            selected = false,
            onClick = { /*TODO*/ },
        colors = RadioButtonDefaults.colors(
            selectedColor = MaterialTheme.colors.primary,
            unselectedColor = MaterialTheme.colors.onBackground,
            disabledColor = Inactivebackground
        ))
        Text(text = sortItem.text)
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

@Preview
@Composable
fun PreviewDialogSortingTask() {
    TuduTheme {
        ScreenDialogSorting(onDismiss = {}, onConfirm = {})
    }
}

@Preview
@Composable
fun PreviewItemDialogSortTask(){
    TuduTheme {
        ItemDialogSortTask(sortItem = SortTask.AlphabeticalDescending)
    }
}