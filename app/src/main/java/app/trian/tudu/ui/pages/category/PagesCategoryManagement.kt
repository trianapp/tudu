package app.trian.tudu.ui.pages.category

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.R
import app.trian.tudu.common.getTheme
import app.trian.tudu.data.local.Category
import app.trian.tudu.domain.ThemeData
import app.trian.tudu.ui.component.dialog.DialogFormCategory
import app.trian.tudu.ui.component.ItemAddCategory
import app.trian.tudu.ui.component.ItemCategory
import app.trian.tudu.ui.component.dialog.DialogDeleteConfirmation
import app.trian.tudu.ui.theme.Inactivebackground
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.TaskViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import compose.icons.Octicons
import compose.icons.octicons.ArrowLeft16
import logcat.LogPriority
import logcat.logcat

@Composable
fun PagesCategoryManagement(
    modifier: Modifier=Modifier,
    router: NavHostController,
    theme:String

) {
    val taskViewModel = hiltViewModel<TaskViewModel>()

    val systemUiController = rememberSystemUiController()
    val isSystemDark = isSystemInDarkTheme()
    val statusBar = MaterialTheme.colors.background

    val listCategory by taskViewModel.listCategory.observeAsState(initial = emptyList())
    var shouldShowDialogFormCategory by remember {
        mutableStateOf(false)
    }
    var shouldShowDialogDelete by remember {
        mutableStateOf(false)
    }
    var currentCategory by remember {
        mutableStateOf(Category())
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
        taskViewModel.getListCategory()
    })

    DialogFormCategory(
        show = shouldShowDialogFormCategory,
        category = currentCategory,
        onHide = {
            shouldShowDialogFormCategory=false
        },
        onSubmit = {

            //check update or add new?
            if(it.categoryId.isBlank()){
                taskViewModel.addNewCategory(it.name)
            }else{
                taskViewModel.updateCategory(it)
            }
            //set current state
            currentCategory = Category()
        }
    )

    DialogDeleteConfirmation(
        show=shouldShowDialogDelete,
        name=currentCategory.name,
        onDismiss = {
            shouldShowDialogDelete = false
        },
        onCancel = {
            shouldShowDialogDelete = false
        },
        onConfirm = {
            //delete category
            taskViewModel.deleteCategory(currentCategory)

            //reset state to initial value
            currentCategory = Category()
            shouldShowDialogDelete = false
        }
    )
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp,
                navigationIcon = {
                    IconToggleButton(checked = false, onCheckedChange = {
                        router.popBackStack()
                    }) {
                        Icon(
                            imageVector = Octicons.ArrowLeft16,
                            contentDescription = ""
                        )
                    }
                },
                title = {
                    Text(text = stringResource(R.string.title_category_management))
                }
            )
        }
    ) {
        LazyColumn(
            content = {
                item {
                    Row (
                        modifier= modifier
                            .fillMaxWidth()
                            .background(Inactivebackground)
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ){
                       Text(
                           text = stringResource(R.string.subtitle_category_management),
                           style= TextStyle(
                               color = Color.Black
                           )
                       )
                    }
                }
                items(listCategory){
                    data ->
                    ItemCategory(
                        category = data,
                        onEdit = {
                            currentCategory = it
                            shouldShowDialogFormCategory = true
                        },
                        onHide = {},
                        onDelete = {
                            currentCategory = it
                            shouldShowDialogDelete = true
                        }
                    )
                }
                item {
                    ItemAddCategory {
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
            router = rememberNavController(),
            theme = ThemeData.DEFAULT.value
        )
    }
}