package app.trian.tudu.ui.pages.task

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.data.local.Task
import app.trian.tudu.data.local.Todo
import app.trian.tudu.domain.DataState
import app.trian.tudu.ui.component.ItemAddTodo
import app.trian.tudu.ui.component.ItemTodo
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.TaskViewModel
import compose.icons.Octicons
import compose.icons.octicons.ArrowLeft16
import compose.icons.octicons.X16

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
    val listTodo by taskViewModel.listTodo.observeAsState(initial = emptyList())
    var taskId by remember {
        mutableStateOf("")
    }



    fun addPlainTodo(){
        taskViewModel.addNewTodo(
            "",
            taskId
        )
    }

    fun updateTodo(todo: Todo){
        taskViewModel.updateTodo(todo)
    }
    fun doneTodo(todo: Todo){
        taskViewModel.doneTodo(todo)
    }
    fun deleteTodo(todo: Todo){
        taskViewModel.deleteTodo(todo)
    }

    LaunchedEffect(key1 = Unit, block = {
        taskId = currentBackStack.value?.arguments?.getString("taskId") ?: ""
        taskViewModel.getTaskById(taskId)
        taskViewModel.getListTodo(taskId)
    })

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                                 IconToggleButton(checked = false, onCheckedChange = {}) {
                                     Icon(
                                         imageVector = Octicons.ArrowLeft16,
                                         contentDescription = ""
                                     )
                                 }
                },
                title = {
                    Text(text = "Detail")

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
                var taskName by remember {
                    mutableStateOf(TextFieldValue(text = (detailTask as DataState.onData<Task>).data.name))
                }
                Box(
                    modifier = modifier.padding(horizontal = 16.dp)
                ) {
                    Column(
                        modifier=modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(text = "Category")
                        Spacer(modifier = modifier.height(16.dp))
                        BasicTextField(
                            modifier=modifier.fillMaxWidth(),
                            value = taskName,
                            onValueChange = {
                                taskName = it

                            },
                            textStyle = TextStyle(
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        LazyColumn(content = {
                            items(listTodo){
                                    data->
                                ItemTodo(
                                    todo = data,
                                    onDone = {
                                             doneTodo(it)
                                    },
                                    onChange = {
                                        updateTodo(it)
                                    },
                                    onDelete = {
                                        deleteTodo(it)
                                    }
                                )
                            }
                            item {
                                ItemAddTodo {
                                    addPlainTodo()
                                }
                            }
                        })

                    }
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

@Preview
@Composable
fun PreviewPageDetailTask(){
    TuduTheme {
        PageDetailTask(router = rememberNavController())
    }
}