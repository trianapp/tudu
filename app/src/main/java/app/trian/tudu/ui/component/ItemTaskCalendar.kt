package app.trian.tudu.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.trian.tudu.ui.theme.TuduTheme

/**
 * Item Task Calendar
 * author Trian Damai
 * created_at 26/02/22 - 21.58
 * site https://trian.app
 */

@Composable
fun ItemTaskCalendar() {
    Row {
        Text(text = "00:00")
        Column {
            Text(text = "Ini Title")
            Text(text = "Reminder")

        }
    }
}

@Preview
@Composable
fun PreviewItemTaskCalendar(){
    TuduTheme {
        ItemTaskCalendar()
    }
}