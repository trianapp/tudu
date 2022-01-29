package app.trian.tudu.ui.pages.dashbboard

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.common.Routes
import app.trian.tudu.ui.component.AppbarHome
import app.trian.tudu.ui.component.BottomSheetInputNewTask
import app.trian.tudu.ui.component.ItemTask
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.TaskViewModel
import compose.icons.Octicons
import compose.icons.octicons.Plus16
import kotlinx.coroutines.launch
import logcat.logcat

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun PageHome(
    modifier:Modifier=Modifier,
    router: NavHostController,
){
    val taskViewModel = hiltViewModel<TaskViewModel>()
    val listTask by taskViewModel.listTask.observeAsState(initial = emptyList())

    val scope = rememberCoroutineScope()
    val ctx = LocalContext.current

    fun hideKeyboard(){
        val activity = (ctx as Activity)
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if(view == null){
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken,0)
    }

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = false,
        confirmStateChange = {
            if(it == ModalBottomSheetValue.Hidden){
                hideKeyboard()
            }
            true
        }
    )
    LaunchedEffect(key1 = Unit, block = {
        taskViewModel.getListTask()
    })

    BasePagesDashboard(
        modalBottomSheetState=modalBottomSheetState,
        topAppbar = {
                    AppbarHome {
                        router.navigate(Routes.CATEGORY)
                    }
        },
        sheetContent={
            BottomSheetInputNewTask(
                onSubmit = {
                    taskViewModel.addNewTask(it)
                    scope.launch {
                        modalBottomSheetState.hide()
                        hideKeyboard()
                    }
                }
            )
        },
        router = router,
    ) {
        Box(modifier = modifier.padding(bottom = 60.dp)) {
            LazyColumn(
                modifier = modifier.fillMaxSize()
            ) {
                items(listTask) { data ->
                    ItemTask(
                        task = data,
                        onDone = {

                        },
                        onDetail = {
                            router.navigate("${Routes.DETAIL_TASK}/${it.taskId}")
                        }
                    )
                }
            }
            FloatingActionButton(
                modifier = modifier
                    .align(Alignment.BottomEnd),
                onClick = {
                    scope.launch {
                        modalBottomSheetState.show()
                    }
                }
            ) {
                Icon(imageVector = Octicons.Plus16, contentDescription = "")
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
        PageHome(router = rememberNavController())
    }
}