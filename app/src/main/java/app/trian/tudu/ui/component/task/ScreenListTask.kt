package app.trian.tudu.ui.component.task

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.common.Routes
import app.trian.tudu.common.gridItems
import app.trian.tudu.data.local.AppSetting
import app.trian.tudu.data.local.Task
import app.trian.tudu.ui.component.ItemTaskGrid
import app.trian.tudu.ui.component.ItemTaskRow
import app.trian.tudu.ui.component.header.HeaderTask
import app.trian.tudu.ui.theme.TuduTheme

/**
 * Screen list task
 * author Trian Damai
 * created_at 30/01/22 - 11.36
 * site https://trian.app
 */

@ExperimentalFoundationApi
@Composable
fun ScreenListTask(
    modifier:Modifier=Modifier,
    listType:HeaderTask,
    listTask:List<Task>,
    appSetting: AppSetting,
    onChangeListType:(type:HeaderTask)->Unit={},
    onDetail:(task:Task)->Unit={},
    onDone:(task:Task)->Unit={},
    onDelete:(task:Task)->Unit={}
) {
    LazyColumn(
        modifier = modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        item{
            HeaderTask(
                onListTypeChange = {
                    onChangeListType(it)
                }
            )
        }
        when(listType){
            HeaderTask.GRID -> {
                gridItems(listTask, columnCount = 2) {
                        data  ->
                    ItemTaskGrid(
                        task = data,
                        dateFormat = appSetting.dateFormat,
                        onDone = {
                            onDone(it)
                        },
                        onDetail = {
                            onDetail(it)

                        },
                        onDelete = {
                            onDelete(it)
                        }
                    )
                }
            }
            HeaderTask.ROW -> {
                items(listTask) { data ->
                    ItemTaskRow(
                        task = data,
                        dateFormat = appSetting.dateFormat,
                        onDone = {
                           onDone(it)
                        },
                        onDetail = {
                            onDetail(it)

                        },
                        onDelete = {
                            onDelete(it)
                        }
                    )
                }
            }
        }

    }
}

@ExperimentalFoundationApi
@Preview(
    uiMode=UI_MODE_NIGHT_NO
)
@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewScreenListTask(){
    TuduTheme {
        ScreenListTask(
            listType = HeaderTask.ROW,
            listTask = listOf(),
            appSetting = AppSetting()
        )
    }
}