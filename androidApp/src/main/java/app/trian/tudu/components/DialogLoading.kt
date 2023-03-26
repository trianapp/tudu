package app.trian.tudu.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy

/**
 * Dialog logout
 * author Trian Damai
 * created_at 01/02/22 - 21.31
 * site https://trian.app
 */
@Composable
fun DialogLoading(
    show: Boolean = false,
    title: String = "Please Wait...",
    message: String = "Updating changes...",
) {
    if (show) {
        AlertDialog(
            modifier = Modifier.padding(
                horizontal = 20.dp
            ),
            onDismissRequest = { },
            dismissButton = {},
            confirmButton = {},
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                   Box(
                       modifier = Modifier.size(50.dp)
                   ) {
                       CircularProgressIndicator()
                   }
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )

                }
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