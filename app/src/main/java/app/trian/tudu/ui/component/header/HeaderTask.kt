package app.trian.tudu.ui.component.header

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.ui.theme.TuduTheme
import compose.icons.FeatherIcons
import compose.icons.Octicons
import compose.icons.feathericons.Grid
import compose.icons.feathericons.Menu
import compose.icons.octicons.ChevronDown16


/**
 * Header task
 * author Trian Damai
 * created_at 30/01/22 - 13.30
 * site https://trian.app
 */
@Composable
fun HeaderTask(
    modifier:Modifier=Modifier,
    filter: Filter=Filter.RECENT,
    type:HeaderTask=HeaderTask.ROW,
    onListTypeChange:(type:HeaderTask)->Unit={},
    onFilterChange:(filter:Filter)->Unit={}
) {
    var selected by remember {
        mutableStateOf(type)
    }
    var filter by remember {
        mutableStateOf(filter)
    }
    Row(
        modifier= modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = when(filter){
                    Filter.PREVIOUS -> "Previous"
                    Filter.COMPLETE -> "Completed Today"
                    Filter.RECENT -> "Recent"
                }
            )
            Spacer(modifier = modifier.width(6.dp))
            Icon(imageVector = Octicons.ChevronDown16, contentDescription = "")
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconToggleButton(
                checked = selected == HeaderTask.ROW,
                enabled = true,
                onCheckedChange = {
                    onListTypeChange(HeaderTask.ROW)
                    selected = HeaderTask.ROW
                }
            ) {
                Icon(
                    imageVector = FeatherIcons.Menu,
                    contentDescription = "",
                    tint = when(selected){
                        HeaderTask.GRID -> Color.DarkGray
                        HeaderTask.ROW -> MaterialTheme.colorScheme.primary
                    }
                )
            }
            IconToggleButton(
                checked = selected == HeaderTask.GRID,
                enabled = true,
                onCheckedChange = {
                    onListTypeChange(HeaderTask.GRID)
                    selected = HeaderTask.GRID
                }
            ) {
                Icon(
                    imageVector = FeatherIcons.Grid,
                    contentDescription = "",
                    tint = when(selected){
                        HeaderTask.GRID -> MaterialTheme.colorScheme.primary
                        HeaderTask.ROW -> Color.DarkGray
                    }
                )
            }
        }
    }
}
enum class HeaderTask{
    GRID,
    ROW
}

enum class Filter{
    PREVIOUS,
    COMPLETE,
    RECENT
}
@Preview
@Composable
fun PreviewHeaderTask() {
    TuduTheme {
        HeaderTask()
    }
}