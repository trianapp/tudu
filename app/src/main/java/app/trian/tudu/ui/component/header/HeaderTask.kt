package app.trian.tudu.ui.component.header

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
            .fillMaxWidth().background(MaterialTheme.colors.background),
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
                },
                style=MaterialTheme.typography.subtitle2.copy(
                    color = MaterialTheme.colors.onBackground
                )
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
                        HeaderTask.ROW -> MaterialTheme.colors.primary
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
                        HeaderTask.GRID -> MaterialTheme.colors.primary
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
@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewHeaderTask() {
    TuduTheme {
        HeaderTask()
    }
}