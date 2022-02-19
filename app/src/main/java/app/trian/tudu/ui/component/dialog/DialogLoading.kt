package app.trian.tudu.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.trian.tudu.ui.theme.TuduTheme

/**
 * Dialog Loading
 * author Trian Damai
 * created_at 19/02/22 - 17.37
 * site https://trian.app
 */
@Composable
fun DialogLoading(
    show:Boolean=false
) {
    if(show){
        Dialog(onDismissRequest = { /*TODO*/ }) {

        }
    }
}

@Composable
fun ScreenDialogLoading(
    modifier: Modifier=Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize(0.3f)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.background)
    ){
        LinearProgressIndicator(
            color = MaterialTheme.colors.primary
        )
    }
}

@Preview
@Composable
fun PreviewDialogLoading() {
    TuduTheme {
        ScreenDialogLoading()
    }
}