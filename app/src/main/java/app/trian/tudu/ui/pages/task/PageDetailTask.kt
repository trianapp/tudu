 package app.trian.tudu.ui.pages.task

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.data.local.Task
import app.trian.tudu.data.local.Todo
import app.trian.tudu.domain.DataState
import app.trian.tudu.ui.component.task.ScreenDetailTask
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.TaskViewModel
import compose.icons.Octicons
import compose.icons.octicons.ArrowLeft16

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
     val ctx = LocalContext.current
     val currentBackStack = router.currentBackStackEntryAsState()
     val taskViewModel = hiltViewModel<TaskViewModel>()
     val detailTask by taskViewModel.detailTask.observeAsState(initial = DataState.LOADING)
     val completeTodo by taskViewModel.completeTodo.observeAsState(initial = emptyList())
     val unCompleteTodo by taskViewModel.unCompleteTodo.observeAsState(initial = emptyList())
     val listCategory by taskViewModel.listCategory.observeAsState(initial = emptyList())
     var taskId by remember { mutableStateOf("") }

     fun addPlainTodo(){
        taskViewModel.addNewTodo(
            "",
            taskId
        )
     }

     fun updateTodo(todo: Todo){
        taskViewModel.updateTodo(todo)
     }
     fun deleteTodo(todo: Todo){
        taskViewModel.deleteTodo(todo)
     }
     fun updateTask(task: Task){
        taskViewModel.updateTask(task)
     }


    LaunchedEffect(key1 = Unit, block = {
        taskId = currentBackStack.value?.arguments?.getString("taskId") ?: ""
        taskViewModel.getTaskById(taskId)
        taskViewModel.getListTodo(taskId)


    })

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colorScheme.background,
                elevation = 0.dp,
                navigationIcon = {
                    IconToggleButton(
                        checked = false, onCheckedChange = {
                            router.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Octicons.ArrowLeft16,
                            contentDescription = ""
                        )
                    }
                },
                title = {

                }
            )
        }
    ) {

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
                val task =  (detailTask as DataState.onData<Task>).data
                ScreenDetailTask(
                    task =task,
                    category = listCategory.filter { it.categoryId == task.category_id }.lastOrNull(),
                    completeTodo = completeTodo,
                    unCompleteTodo=unCompleteTodo,
                    updateTask = {
                        updateTask(it)
                    },
                    addNewTodo = {
                        addPlainTodo()
                    },
                    updateTodo = {
                        updateTodo(it)
                    },
                    deleteTodo = {
                        deleteTodo(it)

                    },
                )
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

@Preview
@Composable
fun PreviewPageDetailTask(){
    TuduTheme {
        PageDetailTask(router = rememberNavController())
    }
}