package app.trian.tudu.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.SecureFlagPolicy
import app.trian.tudu.R
import app.trian.tudu.base.BaseMainApp

/**
 * Dropdown
 * author Trian Damai
 * created_at 31/01/22 - 20.59
 * site https://trian.app
 */
@Composable
fun DropdownActionItemCategory(
    show:Boolean,
    onDismiss:()->Unit={},
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
            text = {
                Text(
                    text = stringResource(id = R.string.btn_edit),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color=MaterialTheme.colorScheme.onBackground
                    )
                )
            },
            onClick = {
                onEdit()
                onDismiss()
            }
        )
        DropdownMenuItem(
            text={
                Text(
                    text = stringResource(id = R.string.btn_delete),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color=MaterialTheme.colorScheme.onBackground
                    )
                )
            },
            onClick = {
                onDelete()
                onDismiss()
            }
        )
    }
}

@Preview
@Composable
fun PreviewDropdownActionItemCategory(){
    BaseMainApp {
        DropdownActionItemCategory(
            show = true,
        )
    }
}