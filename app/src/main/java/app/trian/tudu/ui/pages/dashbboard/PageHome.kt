package app.trian.tudu.ui.pages.dashbboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.TaskViewModel
import compose.icons.Octicons
import compose.icons.octicons.Plus16

@Composable
fun PageHome(
    modifier:Modifier=Modifier,
    router: NavHostController
){
    val taskViewModel = hiltViewModel<TaskViewModel>()
    val listTask by taskViewModel.listTask.observeAsState(initial = emptyList())
    LaunchedEffect(key1 = Unit, block = {
        taskViewModel.getListTask()
    })
    Box(modifier = modifier.padding(bottom = 60.dp)) {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(listTask){
                    data->
                Text(text = data.name)
            }
        }
        FloatingActionButton(
            modifier = modifier
                .align(Alignment.BottomEnd),
            onClick = {
                taskViewModel.addNewTask()
            }
        ) {
            Icon(imageVector = Octicons.Plus16, contentDescription = "")
        }
    }
}

@Preview
@Composable
fun PreviewPageHome(){
    TuduTheme {
        PageHome(router = rememberNavController())
    }
}