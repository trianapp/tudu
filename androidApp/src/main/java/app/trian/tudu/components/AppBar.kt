package app.trian.tudu.components

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.CloudSync
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import app.trian.tudu.R
import app.trian.tudu.base.BaseMainApp
import app.trian.tudu.data.model.CategoryModel

/**
 * App bar home
 * author Trian Damai
 * created_at 29/01/22 - 19.01
 * site https://trian.app
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppbarHome(
    showDropdownOptionCategory: Boolean = false,
    dataCategory: List<CategoryModel> = listOf(),
    onOptionMenuSelected: (menu: Int) -> Unit = {},
    onSelectCategory: (category: CategoryModel) -> Unit = {},
    onShowDropDownOptionCategory: (Boolean) -> Unit = {},
) {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.title_appbar_home),
                    style = MaterialTheme.typography.titleLarge
                )
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            actions = {
                IconToggleButton(
                    checked = showDropdownOptionCategory,
                    onCheckedChange = {
                        onShowDropDownOptionCategory(true)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.MoreVert,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    val listOption = listOf(
                        Pair(
                            Icons.Outlined.Category,
                            R.string.option_category_management
                        ),
                        Pair(
                            Icons.Outlined.Search,
                            R.string.option_search
                        ),
                        Pair(
                            Icons.Outlined.Sort,
                            R.string.option_sort
                        ),
                        Pair(
                            Icons.Outlined.CloudSync,
                            R.string.option_sync
                        )
                    )
                    DropdownMenu(
                        expanded = showDropdownOptionCategory,
                        onDismissRequest = { onShowDropDownOptionCategory(false) },
                    ) {
                        listOption.forEach {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = stringResource(id = it.second),
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    )
                                },
                                leadingIcon = {
                                    Icon(imageVector = it.first, contentDescription = "")
                                },
                                onClick = { onOptionMenuSelected(it.second) }
                            )

                        }
                    }
                }
            }
        )
        TabBarHome(
            tabData = dataCategory,
            onSelect = onSelectCategory
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppbarAuth(
    onBackPressed: () -> Unit = {}
) {
    TopAppBar(
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        navigationIcon = {
            IconToggleButton(checked = false, onCheckedChange = {
                onBackPressed()
            }) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = ""
                )
            }
        },
        title = {}
    )
}

@Composable
fun AppbarBasic(
    title: String,
    onBackPressed: () -> Unit = {}
) {
    TopAppBar(
        navigationIcon = {
            IconToggleButton(
                checked = false,
                onCheckedChange = { onBackPressed() }) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = ""
                )
            }
        },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    )
}

@SuppressLint("NewApi")
@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewAppbarHome() {
    BaseMainApp(
        topAppBar = {
            AppbarHome(
                dataCategory = listOf()
            )
        }
    ) {

    }

}

@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewAppbarBasic() {
    BaseMainApp(
        topAppBar = {
            AppbarBasic(title = "Change password")
        }
    ) {

    }
}