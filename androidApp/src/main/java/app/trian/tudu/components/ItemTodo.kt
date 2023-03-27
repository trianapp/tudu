package app.trian.tudu.components

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.R
import app.trian.tudu.base.BaseMainApp

/**
 * Item todo
 * author Trian Damai
 * created_at 29/01/22 - 22.27
 * site https://trian.app
 */
@Composable
fun ItemTodo(
    modifier: Modifier = Modifier,
    todoDone: Boolean = false,
    todoName: String = "",
    onChange: (String) -> Unit = {},
    onDelete: () -> Unit = {},
    onDone: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surface
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            RadioButton(
                selected = todoDone,
                enabled = true,
                onClick = onDone,
                modifier = modifier
                    .align(
                        Alignment.CenterStart
                    ),
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.primary,
                    unselectedColor = MaterialTheme.colorScheme.onSurface,
                )

            )
            TextField(
                modifier = modifier
                    .fillMaxWidth(fraction = 0.9f)
                    .align(Alignment.CenterStart)
                    .padding(start = 30.dp),
                value = todoName,
                enabled = !todoDone,
                onValueChange = onChange,
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        stringResource(id = R.string.placeholder_input_todo),
                        style = MaterialTheme.typography.labelMedium,
                        textDecoration = if (todoDone) TextDecoration.LineThrough else TextDecoration.None,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                },
                textStyle = MaterialTheme.typography.labelMedium.copy(
                    textDecoration = if (todoDone) TextDecoration.LineThrough else TextDecoration.None,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
        IconToggleButton(
            checked = false,
            onCheckedChange = {
                onDelete()
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = stringResource(R.string.content_description_icon_close),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewItemTodo() {
    BaseMainApp {
        ItemTodo() {}
    }
}