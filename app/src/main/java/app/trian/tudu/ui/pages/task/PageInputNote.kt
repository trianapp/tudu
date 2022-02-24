package app.trian.tudu.ui.pages.task


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.R
import app.trian.tudu.common.getTheme
import app.trian.tudu.data.local.Task
import app.trian.tudu.domain.ThemeData
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.TaskViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import compose.icons.Octicons
import compose.icons.octicons.ArrowLeft24
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Page input note
 * author Trian Damai
 * created_at 01/02/22 - 18.55
 * site https://trian.app
 */

@Composable
fun PageInputNote(
    modifier: Modifier = Modifier,
    router: NavHostController,
    theme:String
) {
    val taskViewModel = hiltViewModel<TaskViewModel>()
    val detailTask by taskViewModel.detailTask.observeAsState(initial = Task())

    val systemUiController = rememberSystemUiController()
    val isSystemDark = isSystemInDarkTheme()
    val statusBar = MaterialTheme.colors.background

    val scope = rememberCoroutineScope()
    var noteState by remember {
        mutableStateOf(TextFieldValue(text = detailTask.note))
    }

    fun updateTask(){
        scope.launch {
            delay(500)
            detailTask.apply { note = noteState.text }
            taskViewModel.updateTask(detailTask)
        }
    }

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = statusBar,
            darkIcons = when(theme.getTheme()){
                ThemeData.DEFAULT -> !isSystemDark
                ThemeData.DARK -> false
                ThemeData.LIGHT -> true
            }
        )
    }
    LaunchedEffect(key1 = Unit, block = {
        val taskId = router.currentBackStackEntry?.arguments?.getString("taskId") ?: ""
        taskViewModel.getTaskById(taskId)
        delay(100)
        noteState = TextFieldValue(text = detailTask.note)
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
                        text = detailTask.note,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    ) {
        LazyColumn(content = {
            item {
                TextField(
                    modifier=modifier.fillMaxWidth().fillMaxHeight(),
                    value = noteState,
                    onValueChange = {
                        noteState = it
                        updateTask()
                    },
                    placeholder={
                        Text(
                            text = noteState.text.ifBlank { stringResource(R.string.placeholder_input_note) },
                            style = TextStyle(
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colors.onBackground
                            )
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        textColor = MaterialTheme.colors.onBackground,
                        backgroundColor = Color.Transparent,
                    )
                )
            }
        })
    }
}

@Preview
@Composable
fun PreviewPageInputNote() {
    TuduTheme {
        PageInputNote(
            router = rememberNavController(),
            theme = ThemeData.DEFAULT.value
        )
    }
}