package app.trian.tudu.ui.component.task

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.ui.theme.InactiveText
import app.trian.tudu.ui.theme.TuduTheme
import compose.icons.Octicons
import compose.icons.octicons.Eye16
import compose.icons.octicons.EyeClosed16

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
    showPasswordObsecure:Boolean=false,
    onChange:(value:String)->Unit={}
) {
    var value by remember {
        mutableStateOf(TextFieldValue(text = initialValue))
    }
    var visible by remember {
        mutableStateOf(!showPasswordObsecure)
    }
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        label.invoke()
        Spacer(modifier = modifier.height(8.dp))
        OutlinedTextField(
            modifier=modifier.fillMaxWidth(),
            visualTransformation=if(visible) VisualTransformation.None else PasswordVisualTransformation(),
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
                if(showPasswordObsecure){
                    IconToggleButton(checked = visible, onCheckedChange = {
                        visible = !visible
                    }) {
                        Icon(
                            imageVector = if(visible) Octicons.Eye16 else Octicons.EyeClosed16,
                            contentDescription = "",
                            tint = if (visible) InactiveText else MaterialTheme.colors.primary
                        )
                    }
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
            showPasswordObsecure = true
        )
    }
}