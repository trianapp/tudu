package app.trian.tudu.feature.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.trian.tudu.R
import app.trian.tudu.ApplicationState
import app.trian.tudu.base.BaseMainApp
import app.trian.tudu.base.UIWrapper
import app.trian.tudu.components.DialogDeleteConfirmation
import app.trian.tudu.components.DialogFormCategory
import app.trian.tudu.components.ItemAddCategory
import app.trian.tudu.components.ItemCategory

object Category {
    const val routeName = "Category"
}

fun NavGraphBuilder.routeCategory(
    state: ApplicationState,
) {
    composable(Category.routeName) {
        ScreenCategory(appState = state)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ScreenCategory(
    appState: ApplicationState,
) = UIWrapper<CategoryViewModel>(appState = appState) {
    val state by uiState.collectAsState()
    val dataState by uiDataState.collectAsState()

    with(appState) {
        hideBottomAppBar()
        setupTopAppBar {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                navigationIcon = {
                    IconToggleButton(checked = false, onCheckedChange = {
                        router.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                title = {
                    Text(text = stringResource(R.string.title_category_management))
                }
            )
        }
    }

    DialogFormCategory(
        show = state.showFormCategory,
        categoryName = state.categoryName,
        onChange = {
            dispatch(CategoryEvent.SetCategoryName(it))
        },
        onHide = {
            dispatch(CategoryEvent.ShowFormCategory(false))
        },
        onSubmit = {
            dispatch(CategoryEvent.SubmitCategory)
        }
    )

    DialogDeleteConfirmation(
        show = state.showDialogDeleteCategory,
        name = state.categoryName,
        onCancel = {
            dispatch(CategoryEvent.ShowDialogDeleteCategory(false))
        },
        onConfirm = {
            dispatch(CategoryEvent.DeleteCategory)
        },
        onDismiss = {
            dispatch(CategoryEvent.ShowDialogDeleteCategory(false))
        }
    )

    LazyColumn(
        content = {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.subtitle_category_management),
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
            items(dataState.category) {
                ItemCategory(
                    categoryName = it.category.categoryName,
                    categoryCount = it.count,
                    onEdit = {
                        dispatch(
                            CategoryEvent.SetUpdateCategory(
                                categoryId = it.category.categoryId,
                                categoryName = it.category.categoryName
                            )
                        )
                    },
                    onDelete = {
                        commit {
                            copy(
                                categoryId = it.category.categoryId,
                                categoryName = it.category.categoryName
                            )
                        }
                        dispatch(
                            CategoryEvent.ShowDialogDeleteCategory(true)
                        )
                    }
                )
            }

            item {
                ItemAddCategory {
                    dispatch(CategoryEvent.ShowFormCategory(true))
                }
            }
        }
    )

}


@Preview
@Composable
fun PreviewScreenCategory() {
    BaseMainApp(
        topAppBar = {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                navigationIcon = {
                    IconToggleButton(checked = false, onCheckedChange = {
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
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
        ScreenCategory(it)
    }
}