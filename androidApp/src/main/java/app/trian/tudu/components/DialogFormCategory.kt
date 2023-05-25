package app.trian.tudu.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import app.trian.tudu.R
import app.trian.tudu.base.BaseMainApp

/**
 * Dialog Form Category
 * author Trian Damai
 * created_at 29/01/22 - 20.00
 * site https://trian.app
 */
@Composable
fun DialogFormCategory(
    show: Boolean = false,
    categoryName: String,
    onChange:(String) ->Unit={},
    onHide: () -> Unit={},
    onSubmit: (String) -> Unit={}
) {
    if (show) {
        Dialog(
            onDismissRequest = {
                onHide()
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            ) {
                ScreenDialogFormCategory(
                    onChange=onChange,
                    onSubmit = onSubmit,
                    onHide = onHide,
                    categoryName = categoryName
                )
            }
        }
    }
}

@Composable
fun ScreenDialogFormCategory(
    modifier: Modifier = Modifier,
    categoryName: String,
    onChange: (String) -> Unit={},
    onHide: () -> Unit = {},
    onSubmit: (String) -> Unit={}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                MaterialTheme.shapes.small
            )
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Column {
            Text(
                text = stringResource(R.string.title_create_new_category),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Spacer(modifier = modifier.height(16.dp))
            Box(
                modifier = modifier
                    .clip( MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                TextField(
                    modifier = modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    value = categoryName,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.placeholder_input_category),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.tertiary,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    onValueChange = {
                        if (it.length <= 50) {
                            onChange(it)
                        }

                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    shape =  MaterialTheme.shapes.small,
                )
                Text(
                    text = stringResource(
                        R.string.input_category_count,
                        categoryName.length
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomEnd)
                        .padding(
                            top = 4.dp,
                            end = 10.dp,
                            bottom = 4.dp
                        ),
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = modifier.height(24.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = {
                    onHide()
                }) {
                    Text(
                        text = stringResource(R.string.btn_cancel),
                        style = MaterialTheme.typography.displayMedium.copy(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                            fontSize = 18.sp
                        )
                    )
                }
                TextButton(onClick = { onSubmit(categoryName) }) {
                    Text(
                        text = stringResource(R.string.btn_save),
                        style = MaterialTheme.typography.displayMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 18.sp
                        )
                    )
                }
            }
        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun PreviewScreenFromCategory() {
    BaseMainApp {
        ScreenDialogFormCategory(
            categoryName = "",
            onSubmit = {}
        )
    }
}