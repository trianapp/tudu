package app.trian.tudu.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.trian.tudu.R
import app.trian.tudu.base.BaseMainApp

/**
 * Dialog logout
 * author Trian Damai
 * created_at 01/02/22 - 21.31
 * site https://trian.app
 */
@Composable
fun DialogDeleteConfirmation(
    show:Boolean=false,
    name:String="",
    onCancel:()->Unit={},
    onConfirm:()->Unit={},
    onDismiss:()->Unit={}
) {
    if(show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            ScreenDialogDeleteConfirmation(
                name=name,
                onCancel = onCancel,
                onConfirm = onConfirm
            )
        }
    }
}

@Composable
fun ScreenDialogDeleteConfirmation(
    modifier: Modifier =Modifier,
    name:String="",
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    all = 16.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.text_confirmation_delete,name),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color=MaterialTheme.colorScheme.onSurface
                )
            )
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                TextButton(onClick = { onCancel() }) {
                    Text(
                        text = stringResource(id = R.string.btn_cancel),
                        style = MaterialTheme.typography.bodyMedium,
                        color=MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                    )
                }
                TextButton(onClick = { onConfirm() }) {
                    Text(
                        text = stringResource(R.string.btn_delete),
                        style = MaterialTheme.typography.bodyMedium,
                        color=MaterialTheme.colorScheme.primary
                    )
                }
            }
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
fun PreviewDialogDeleteConfirmation(){
    BaseMainApp {
        ScreenDialogDeleteConfirmation(
            onCancel = {},
            onConfirm = {}
        )
    }
}