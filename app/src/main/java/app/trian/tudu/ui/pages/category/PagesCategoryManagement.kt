package app.trian.tudu.ui.pages.category

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.ui.component.DialogFormCategory
import app.trian.tudu.ui.component.ItemAddCategory
import app.trian.tudu.ui.component.ItemCategory
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.TaskViewModel
import logcat.logcat

@Composable
fun PagesCategoryManagement(
    modifier: Modifier=Modifier,
    router: NavHostController
) {
    val taskViewModel = hiltViewModel<TaskViewModel>()
    val listCategory by taskViewModel.listCategory.observeAsState(initial = emptyList())
    var shouldShowDialogFormCategory by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit, block = {
        taskViewModel.getListCategory()
    })

    DialogFormCategory(
        show = shouldShowDialogFormCategory,
        onHide = {
            shouldShowDialogFormCategory=false
        },
        onSubmit = {
            taskViewModel.addNewCategory(it)
        }
    )

    Scaffold {
        LazyColumn(
            content = {
                item {
                    Row (
                        modifier= modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 6.dp
                            ),
                        horizontalArrangement = Arrangement.Center
                    ){
                       Text(text = "Category display on homepage")
                    }
                }
                items(listCategory){
                    data ->
                    ItemCategory(category = data)
                }
                item {
                    ItemAddCategory {
                        logcat("harus pokonya") {"iaiia"  }
                        shouldShowDialogFormCategory=true
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewCategoryManagement(){
    TuduTheme {
        PagesCategoryManagement(
            router = rememberNavController()
        )
    }
}