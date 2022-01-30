package app.trian.tudu.ui.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.trian.tudu.R
import app.trian.tudu.ui.theme.TuduTheme
import logcat.logcat

/**
 * Dialog Form Category
 * author Trian Damai
 * created_at 29/01/22 - 20.00
 * site https://trian.app
 */
@Composable
fun DialogFormCategory(
    show:Boolean=false,
    onHide:()->Unit,
    onSubmit:(value:String)->Unit
) {
    if(show) {
        Dialog(onDismissRequest = {
            onHide()
        }) {
            ScreenDialogFormCategory(
                onSubmit = onSubmit,
                onHide = onHide
            )
        }
    }
}

@Composable
fun ScreenDialogFormCategory(
    modifier: Modifier=Modifier,
    onHide: () -> Unit={},
    onSubmit: (value:String) -> Unit
) {
    val ctx = LocalContext.current
    var categoryName by remember {
        mutableStateOf("")
    }
    fun submit(){
        if(categoryName.isBlank()){
            Toast.makeText(ctx,ctx.getString(R.string.blank_validation,"Category"),Toast.LENGTH_LONG).show()
        }else{
            onSubmit(categoryName)
            onHide()
        }
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Column {
            Text(text = stringResource(R.string.title_create_new_cateogory))
            TextField(
                modifier=modifier.fillMaxWidth(),
                value = categoryName,
                placeholder = {
                    Text(text = stringResource(R.string.placeholder_input_category))
                },
                onValueChange ={
                    categoryName = it
                }
            )
            Row(
                modifier=modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = {
                    onHide()
                }) {
                    Text(text = stringResource(R.string.btn_cancel))
                }
                TextButton(onClick = { submit() }) {
                    Text(text = stringResource(R.string.btn_save))
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewScreenFromCategory(){
    TuduTheme {
        ScreenDialogFormCategory{}
    }
}