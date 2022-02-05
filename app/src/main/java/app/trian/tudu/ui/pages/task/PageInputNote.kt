package app.trian.tudu.ui.pages.task


import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.data.local.Task
import app.trian.tudu.domain.DataState
import app.trian.tudu.ui.component.task.ScreenInputNote
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.TaskViewModel
import compose.icons.Octicons
import compose.icons.octicons.ArrowLeft24

/**
 * Page input note
 * author Trian Damai
 * created_at 01/02/22 - 18.55
 * site https://trian.app
 */

@Composable
fun PageInputNote(
    modifier: Modifier = Modifier,
    router: NavHostController
) {
    val taskViewModel = hiltViewModel<TaskViewModel>()
    val detailTask by taskViewModel.detailTask.observeAsState(initial = DataState.LOADING)

    fun updateTask(task: Task){
        taskViewModel.updateTask(task)
    }
    LaunchedEffect(key1 = Unit, block = {
        val taskId = router.currentBackStackEntry?.arguments?.getString("taskId") ?: ""
        taskViewModel.getTaskById(taskId)
    })

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp,
                navigationIcon = {
                    IconToggleButton(
                        checked = false,
                        onCheckedChange = {
                            router.popBackStack()
                        }
                    ) {
                         Icon(
                             imageVector = Octicons.ArrowLeft24,
                             contentDescription = "",
                             tint = MaterialTheme.colors.onBackground
                         )

                    }
                },
                title = {
                    Text(
                        text = when (detailTask){
                            DataState.LOADING -> ""
                            is DataState.onData -> (detailTask as DataState.onData<Task>).data.name
                            is DataState.onFailure -> ""
                        },
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    ) {
       when(detailTask){
           DataState.LOADING -> {}
           is DataState.onData -> {
               ScreenInputNote(
                   task = (detailTask as DataState.onData<Task>).data,
                   onEdit = {
                       updateTask(it)
                   }
               )
           }
           is DataState.onFailure -> {}
       }
    }
}

@Preview
@Composable
fun PreviewPageInputNote() {
    TuduTheme {
        PageInputNote(router = rememberNavController())
    }
}