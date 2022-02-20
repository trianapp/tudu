package app.trian.tudu.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
        contentAlignment = Alignment.Center,
        modifier= modifier
            .size(100.dp)
            .background(
                MaterialTheme.colors.background,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        CircularProgressIndicator(
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