package app.trian.tudu.ui.pages.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import app.trian.tudu.data.local.Task
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
    val detailTask by taskViewModel.detailtask.observeAsState(initial = DataState.LOADING)
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
                Column(
                    modifier=modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text((detailTask as DataState.onData<Task>).data.name)

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