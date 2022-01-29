package app.trian.tudu.ui.pages.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import app.trian.tudu.domain.DataState
import app.trian.tudu.viewmodel.TaskViewModel

/**
 * Detail task
 * author Trian Damai
 * created_at 29/01/22 - 17.28
 * site https://trian.app
 */
@Composable
fun PageDetailTask(
    modifier: Modifier = Modifier,
    router: NavHostController
) {
    val currentBackStack = router.currentBackStackEntryAsState()
    val taskViewModel = hiltViewModel<TaskViewModel>()
    val detailTask by taskViewModel.detailTask.observeAsState(initial = DataState.LOADING)
    LaunchedEffect(key1 = Unit, block = {
        val taskId = currentBackStack.value?.arguments?.getString("taskId") ?: ""
        taskViewModel.getTaskById(taskId)
    })

    Scaffold {
        when(detailTask){
            DataState.LOADING -> {
                Column(
                    modifier=modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Fetch data")
                    CircularProgressIndicator()
                }
            }
            is DataState.onData -> {
                var taskName by remember {
                    mutableStateOf((detailTask as DataState.onData).data.name)
                }
                Column(
                    modifier=modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Category")
                    TextField(
                        modifier=modifier.fillMaxWidth(),
                        value = taskName,
                        onValueChange = {
                            taskName = it
                        },
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                    LazyColumn(content = {

                    })

                }
            }
            is DataState.onFailure -> {
                Column(
                    modifier=modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Failed getting data")

                }
            }
        }
    }

}