package app.trian.tudu.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BrowseGallery
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@Composable
fun DialogTakePicture(
    show: Boolean = false,
    title: String = "",
    openCamera: () -> Unit = {},
    openGallery: () -> Unit = {},
    dismiss: () -> Unit = {}
) {
    if (show) {
        AlertDialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            modifier = Modifier.padding(
                horizontal = 20.dp
            ),
            onDismissRequest = dismiss,
            confirmButton = {},
            title = {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Column {
                    ListItem(
                        colors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Outlined.CameraAlt, contentDescription = ""
                            )
                        },
                        supportingText = {},
                        headlineText = {
                            Text(text = "Camera")
                        },
                        modifier = Modifier.clickable { openCamera() }
                    )
                    ListItem(
                        leadingContent = {
                            Icon(imageVector = Icons.Outlined.BrowseGallery, contentDescription = "")
                        },
                        supportingText = {},
                        headlineText = {
                            Text(text = "Gallery")
                        },
                        modifier = Modifier.clickable { openGallery() }
                    )
                }
            },
        )
    }


}