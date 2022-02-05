package app.trian.tudu.ui.component.dialog

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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
                onConfirm = onConfirm,
                onDismiss = onDismiss
            )
        }
    }
}

@Composable
fun ScreenDialogLogout(
    modifier: Modifier=Modifier,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface)
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
            Text(text = "Are you sure sign out from app?")
            Row(
                modifier = modifier.fillMaxWidth().wrapContentHeight(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                TextButton(onClick = { onCancel() }) {
                    Text(text = "Cancel")
                }
                TextButton(onClick = { onConfirm() }) {
                    Text(text = "Sign out")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewDialogLogout(){
    TuduTheme {
        ScreenDialogLogout(
            onCancel = {},
            onDismiss = {},
            onConfirm = {}
        )
    }
}