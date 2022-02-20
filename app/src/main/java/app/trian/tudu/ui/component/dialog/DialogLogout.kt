package app.trian.tudu.ui.component.dialog

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.trian.tudu.R
import app.trian.tudu.ui.theme.TuduTheme

/**
 * Dialog logout
 * author Trian Damai
 * created_at 01/02/22 - 21.31
 * site https://trian.app
 */
@Composable
fun DialogLogout(
    show:Boolean=false,
    onCancel:()->Unit={},
    onConfirm:()->Unit={},
    onDismiss:()->Unit={}
) {
    if(show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            ScreenDialogLogout(
                onCancel = onCancel,
                onConfirm = onConfirm
            )
        }
    }
}

@Composable
fun ScreenDialogLogout(
    modifier: Modifier=Modifier,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.background)
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
                text = stringResource(R.string.text_confirmation_signout),
                style = MaterialTheme.typography.body1.copy(
                    color=MaterialTheme.colors.onBackground
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
                        style = MaterialTheme.typography.button.copy(
                            color=MaterialTheme.colors.primary.copy(alpha = 0.6f)
                        )
                    )
                }
                TextButton(onClick = { onConfirm() }) {
                    Text(
                        text = stringResource(R.string.btn_signout),
                        style = MaterialTheme.typography.button.copy(
                            color=MaterialTheme.colors.primary
                        )
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
fun PreviewDialogLogout(){
    TuduTheme {
        ScreenDialogLogout(
            onCancel = {},
            onConfirm = {}
        )
    }
}