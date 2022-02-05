package app.trian.tudu.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import app.trian.tudu.R
import app.trian.tudu.ui.theme.TuduTheme

/**
 * Dropdoewn tab bar category
 * author Trian Damai
 * created_at 05/02/22 - 18.01
 * site https://trian.app
 */
@Composable
fun DropdownActionTabCategory(
    modifier: Modifier=Modifier,
    show:Boolean,
    onDismiss:()->Unit={},
    onSelected:(option:Int)->Unit={}
) {
    val listOption = listOf(
        R.string.option_category_management,
        R.string.option_search,
        R.string.option_sort
    )
    DropdownMenu(
        modifier=modifier.background(MaterialTheme.colors.background),
        expanded =show,
        onDismissRequest = onDismiss,
    ) {
        listOption.forEach {
            DropdownMenuItem(onClick = {
                onSelected(it)
            }) {
                Text(
                    text = stringResource(id = it),
                    style = TextStyle(
                        color = MaterialTheme.colors.onBackground
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewDropdownActionTabCategory() {
    TuduTheme {
        DropdownActionTabCategory(show = true)
    }
}