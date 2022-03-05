package app.trian.tudu.ui.pages.dashbboard

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.R
import app.trian.tudu.common.Routes
import app.trian.tudu.common.getTheme
import app.trian.tudu.common.hideKeyboard
import app.trian.tudu.common.signOut
import app.trian.tudu.data.local.AppSetting
import app.trian.tudu.data.local.Category
import app.trian.tudu.data.local.Task
import app.trian.tudu.domain.ThemeData
import app.trian.tudu.ui.component.AppbarHome
import app.trian.tudu.ui.component.dialog.DialogFormCategory
import app.trian.tudu.ui.component.dialog.DialogSortingTask
import app.trian.tudu.ui.component.task.BottomSheetInputNewTask
import app.trian.tudu.ui.component.header.HeaderTask
import app.trian.tudu.ui.component.task.ScreenEmptyTask
import app.trian.tudu.ui.component.task.ScreenListTask
import app.trian.tudu.ui.theme.HexToJetpackColor
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.TaskViewModel
import app.trian.tudu.viewmodel.UserViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import compose.icons.Octicons
import compose.icons.octicons.Plus16
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

@SuppressLint("NewApi")
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun PageHome(
    modifier:Modifier=Modifier,
    router: NavHostController,
    theme:String,
    onChangeTheme:(theme:String)->Unit,
    restartActivity:()->Unit

){
    val taskViewModel = hiltViewModel<TaskViewModel>()
    val userViewModel = hiltViewModel<UserViewModel>()

    val listTask by taskViewModel.listTask.observeAsState(initial = emptyList())
    val listCategory by taskViewModel.listCategory.observeAsState(initial = listOf(Category(
        name = "All",
        created_at = OffsetDateTime.now(),
        updated_at = OffsetDateTime.now(),
        color = HexToJetpackColor.Blue
    )))
    val currentUser by userViewModel.currentUser.observeAsState()
    val appSetting by userViewModel.appSetting.observeAsState(initial = AppSetting())

    val scope = rememberCoroutineScope()
    val ctx = LocalContext.current

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = false,
        confirmStateChange = {
            if(it == ModalBottomSheetValue.Hidden){
                ctx.hideKeyboard()
            }
            true
        }
    )
    var listType by remember {
        mutableStateOf(HeaderTask.ROW)
    }
    var shouldShowDialogAddCategory by remember {
        mutableStateOf(false)
    }

    var shouldShowDialogPickSortTask by remember {
        mutableStateOf(false)
    }

    fun updateTask(task:Task){
        taskViewModel.updateTask(task)
        taskViewModel.getListTask()
    }



    LaunchedEffect(key1 = Unit, block = {
        userViewModel.getCurrentSetting()
        taskViewModel.getListTask()
        taskViewModel.getListCategory()
        userViewModel.getCurrentUser()
        userViewModel.registerNewToken()
    })

    DialogFormCategory(
        show = shouldShowDialogAddCategory,
        onHide = {
            shouldShowDialogAddCategory = false
        },
        onSubmit = {
            taskViewModel.addNewCategory(it.name)
        }
    )

    DialogSortingTask(
        show = shouldShowDialogPickSortTask,
        onDismiss = {shouldShowDialogPickSortTask=false},
        onConfirm = {

        }
    )

    BasePagesDashboard(
        modalBottomSheetState=modalBottomSheetState,
        currentUser = currentUser,
        enableDrawerGesture = true,
        theme = theme,
        onChangeTheme = onChangeTheme,
        topAppbar = {
            AppbarHome(
                dataCategory = listCategory,
                onOptionMenuSelected = {
                    when(it){
                        R.string.option_category_management->{
                            router.navigate(Routes.CATEGORY)
                        }
                        R.string.option_search->{
                            router.navigate(Routes.SEARCH_TASK)
                        }
                        R.string.option_sort->{
                            shouldShowDialogPickSortTask=true
                        }
                    }

                },
                onSelectCategory = {
                    if(it.name == "All"){
                        taskViewModel.getListTask()
                    }else {
                        taskViewModel.getListTaskByCategory(it.categoryId)
                    }
                }
            )
        },
        onLogout = {
            userViewModel.signOut{
                restartActivity()
            }
        },
        sheetContent={
            BottomSheetInputNewTask(
                listCategory = listCategory,
                onSubmit = {
                    task,todo->
                    taskViewModel.addNewTask(task,todo)
                    scope.launch {
                        modalBottomSheetState.hide()
                        ctx.hideKeyboard()
                    }
                },
                onAddCategory = {
                    shouldShowDialogAddCategory = true
                }
            )
        },
        router = router,
    ) {
        Box(
            modifier = modifier
                .padding(bottom = 60.dp)
                .fillMaxSize()
        ) {
            when(listTask.isEmpty()){
                true->{
                    ScreenEmptyTask()
                }
                false->{
                    ScreenListTask(
                        listType = listType,
                        listTask = listTask,
                        appSetting = appSetting,
                        onDetail = {
                            router.navigate("${Routes.DETAIL_TASK}/${it.taskId}")
                        },
                        onDone = {
                            updateTask(it)
                        },
                        onChangeListType = {
                            listType = it
                        }
                    )
                }
            }
            FloatingActionButton(
                backgroundColor=MaterialTheme.colors.primary,
                modifier = modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 30.dp),
                onClick = {
                    scope.launch {
                        modalBottomSheetState.show()
                    }
                }
            ) {
                Icon(
                    imageVector = Octicons.Plus16,
                    contentDescription = stringResource(R.string.content_description_fab_add_task),
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewPageHome(){
    TuduTheme {
        PageHome(
            router = rememberNavController(),
            theme = "",
            onChangeTheme = {},
            restartActivity = {}
        )
    }
}