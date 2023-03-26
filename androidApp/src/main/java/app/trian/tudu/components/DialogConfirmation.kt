package app.trian.tudu.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import app.trian.tudu.R

/**
 * Dialog logout
 * author Trian Damai
 * created_at 01/02/22 - 21.31
 * site https://trian.app
 */
@Composable
fun DialogConfirmation(
    show: Boolean = false,
    message: String = "",
    onCancel: () -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    if (show) {
        AlertDialog(
            modifier = Modifier.padding(
                horizontal = 16.dp
            ),
            onDismissRequest = { },
            dismissButton = {
                TextButton(
                    onClick = { onCancel() },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_cancel),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                        )
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { onConfirm() },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = stringResource(R.string.btn_confirm),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            },
            title = {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            },
            shape = MaterialTheme.shapes.medium,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                securePolicy = SecureFlagPolicy.SecureOn,
                usePlatformDefaultWidth = false
            ),
            tonalElevation = 10.dp
        )
    }
}