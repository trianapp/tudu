package app.trian.tudu.components

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import app.trian.tudu.R
import app.trian.tudu.base.BaseMainApp

/**
 * Item Category
 * author Trian Damai
 * created_at 29/01/22 - 19.41
 * site https://trian.app
 */

@Composable
fun ItemCategory(
    categoryName: String,
    categoryCount: Int,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    var showDropdownAction by remember {
        mutableStateOf(false)
    }
    ListItem(
        leadingContent = {},
        trailingContent = {
            IconToggleButton(
                checked = showDropdownAction,
                onCheckedChange = { showDropdownAction = true }
            ) {
                DropdownActionItemCategory(
                    show = showDropdownAction,
                    onDelete = { onDelete() },
                    onEdit = { onEdit() },
                    onDismiss = { showDropdownAction = false }
                )
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = stringResource(R.string.content_description_option_menu_category),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        headlineText = {
            Text(
                text = categoryName,
                style = MaterialTheme.typography.bodyLarge.copy(
                    MaterialTheme.colorScheme.onSurface
                )
            )
        },
        overlineText = {},
        supportingText = {
            Text(
                text = if (categoryCount > 0) {
                    "Used $categoryCount times"
                } else "No usage",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
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
fun PreviewItemCategory() {
    BaseMainApp {
        ItemCategory(
            categoryName = "Wish list",
            categoryCount = 30
        )
    }
}