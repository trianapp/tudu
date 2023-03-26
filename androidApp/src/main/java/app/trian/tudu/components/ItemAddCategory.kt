package app.trian.tudu.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import app.trian.tudu.R
import app.trian.tudu.base.BaseMainApp

@Composable
fun ItemAddCategory(
    onAdd: () -> Unit
) {
    DropdownMenuItem(
        onClick = { onAdd() },
        text = {
            Text(
                text = stringResource(id = R.string.create_new_category),
                style = MaterialTheme.typography.titleSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = stringResource(R.string.content_description_add_new_category),
                tint = MaterialTheme.colorScheme.onSurface
            )

        }
    )

}

@Preview
@Composable
fun PreviewItemAddCategory() {
    BaseMainApp {
        ItemAddCategory() {

        }
    }
}