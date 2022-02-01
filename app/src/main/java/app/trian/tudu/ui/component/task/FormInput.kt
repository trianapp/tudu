package app.trian.tudu.ui.component.task

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.ui.theme.TuduTheme
import compose.icons.Octicons
import compose.icons.octicons.Eye16

/**
 * FormInpit
 * author Trian Damai
 * created_at 30/01/22 - 09.02
 * site https://trian.app
 */
@Composable
fun FormInput(
    modifier: Modifier=Modifier,
    initialValue:String="",
    placeholder:String="",
    label: @Composable ()->Unit={},
    shoPasswordObsecure:Boolean=false,
    onChange:(value:String)->Unit={}
) {
    var value by remember {
        mutableStateOf(TextFieldValue(text = initialValue))
    }
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        label.invoke()
        Spacer(modifier = modifier.height(8.dp))
        OutlinedTextField(
            modifier=modifier.fillMaxWidth(),
            value = value,
            placeholder = {
                Text(text = placeholder)
            },
            onValueChange = {
                value = it
                onChange(it.text)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
            ),
            shape = RoundedCornerShape(10.dp),
            trailingIcon = {
                if(shoPasswordObsecure){
                    Icon(imageVector = Octicons.Eye16, contentDescription = "")
                }
            }
        )
        Spacer(modifier = modifier.height(10.dp))
    }
}

@Preview
@Composable
fun PreviewFormInput() {
    TuduTheme {
        FormInput(
            shoPasswordObsecure = true
        )
    }
}