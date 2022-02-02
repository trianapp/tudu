package app.trian.tudu.ui.component.task

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.common.Routes
import app.trian.tudu.common.gridItems
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

@Composable
fun ScreenListTask(
    modifier:Modifier=Modifier,
    listType:HeaderTask,
    listTask:List<Task>,
    onChangeListType:(type:HeaderTask)->Unit={},
    onDetail:(task:Task)->Unit={},
    onDone:(task:Task)->Unit={}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
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
                        onDone = {
                            onDone(it)
                        },
                        onDetail = {
                            onDetail(it)

                        }
                    )
                }
            }
            HeaderTask.ROW -> {
                items(listTask) { data ->
                    ItemTaskRow(
                        task = data,
                        onDone = {
                                 onDone(it)
                        },
                        onDetail = {
                            onDetail(it)

                        }
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun PreviewScreenListtask(){
    TuduTheme {

    }
}