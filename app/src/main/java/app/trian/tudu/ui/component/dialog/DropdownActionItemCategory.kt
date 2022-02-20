package app.trian.tudu.ui.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.SecureFlagPolicy
import app.trian.tudu.R
import app.trian.tudu.data.local.Category
import app.trian.tudu.ui.theme.TuduTheme
import compose.icons.Octicons
import compose.icons.octicons.Plus16

/**
 * Dropdown
 * author Trian Damai
 * created_at 31/01/22 - 20.59
 * site https://trian.app
 */
@Composable
fun DropdownActionItemCategory(
    modifier:Modifier=Modifier,
    show:Boolean,
    onDismiss:()->Unit={},
    onHide:()->Unit={},
    onEdit:()->Unit={},
    onDelete:()->Unit={}
){
    DropdownMenu(
        expanded = show,
        onDismissRequest = {
            onDismiss()
        },
        properties = PopupProperties(
            dismissOnBackPress = true,
            focusable = true,
            dismissOnClickOutside = true,
            excludeFromSystemGesture = true,
            clippingEnabled = true,
            securePolicy = SecureFlagPolicy.SecureOff
        )
    ) {
        DropdownMenuItem(
            onClick = {
                onEdit()
                onDismiss()
            }
        ) {
            Text(
                text = stringResource(id = R.string.btn_edit),
                style = MaterialTheme.typography.button.copy(
                    color=MaterialTheme.colors.onBackground
                )
            )
        }
        DropdownMenuItem(
            onClick = {
                onHide()
                onDismiss()
            }
        ) {
            Text(
                text = stringResource(id = R.string.btn_hide),
                style = MaterialTheme.typography.button.copy(
                    color= MaterialTheme.colors.onBackground
                )
            )
        }
        DropdownMenuItem(
            onClick = {
                onDelete()
                onDismiss()
            }
        ) {
            Text(
                text = stringResource(id = R.string.btn_delete),
                style = MaterialTheme.typography.button.copy(
                    color=MaterialTheme.colors.onBackground
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewDropdownActionItemCategory(){
    TuduTheme {
        DropdownPickCategory(
            show = true,
            listCategory = listOf()
        )
    }
}