package app.trian.tudu.ui.component.dialog

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import app.trian.tudu.R
import app.trian.tudu.ui.theme.InactiveText
import app.trian.tudu.ui.theme.TuduTheme

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
        mutableStateOf(TextFieldValue(text = ""))
    }
    var canInput by remember {
        mutableStateOf(true)
    }
    fun submit(){
        if(categoryName.text.isBlank()){
            Toast.makeText(ctx,ctx.getString(R.string.blank_validation,"Category"),Toast.LENGTH_LONG).show()
        }else{
            onSubmit(categoryName.text)
            onHide()
        }
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomStart = 10.dp,
                    bottomEnd = 10.dp
                )
            )
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Column {
            Text(
                text = stringResource(R.string.title_create_new_cateogory),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = modifier.height(16.dp))
            Box (
                modifier = modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colors.surface)
                    ){
                TextField(
                    modifier= modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    value = categoryName,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.placeholder_input_category),
                            style=TextStyle(
                                color = MaterialTheme.colors.onBackground
                            )
                        )
                    },
                    onValueChange ={
                        if(it.text.length <= 50) {
                            categoryName = it
                        }

                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    shape = RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp,
                        bottomStart = 10.dp,
                        bottomEnd = 10.dp
                    ),
                )
                Text(
                    text = stringResource(
                        R.string.input_category_count,
                        categoryName.text.length
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
                    color = InactiveText
                )
            }

            Row(
                modifier=modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = {
                    onHide()
                }) {
                    Text(
                        text = stringResource(R.string.btn_cancel),
                        style = TextStyle(
                            color=MaterialTheme.colors.primary.copy(alpha = 0.6f)
                        )
                    )
                }
                TextButton(onClick = { submit() }) {
                    Text(
                        text = stringResource(R.string.btn_save),
                        style = TextStyle(
                            color=MaterialTheme.colors.primary
                        )
                    )
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