package app.trian.tudu.ui.component.dialog

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import app.trian.tudu.R
import app.trian.tudu.ui.theme.Inactivebackground
import app.trian.tudu.ui.theme.TuduTheme

/**
 * Dialog sorting task
 * author Trian Damai
 * created_at 05/02/22 - 10.37
 * site https://trian.app
 */

@Composable
fun DialogSelect(
    modifier: Modifier=Modifier,
    show:Boolean=false,
    title:String="",
    caption:String="",
    items:List<DialogItemModel> = listOf(),
    selectedItem:DialogItemModel? = null,
    onDismiss:()->Unit={},
    onSelected:(sort:DialogItemModel)->Unit={}
) {
    if(show){
        Dialog(
            onDismissRequest =  onDismiss,
            properties = DialogProperties(

            )
        ) {
            ScreenDialogSelectTheme(
                title=title,
                items=items,
                caption=caption,
                selectedItem=selectedItem,
                onDismiss = onDismiss,
                onSelected = onSelected
            )
        }
    }
}

@Composable
fun ScreenDialogSelectTheme(
    modifier: Modifier=Modifier,
    title: String,
    caption:String,
    items:List<DialogItemModel> = listOf(),
    selectedItem:DialogItemModel? = null,
    onDismiss:()->Unit={},
    onSelected:(sort:DialogItemModel)->Unit={}
) {
    var selected by remember {
        mutableStateOf(selectedItem)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface)
    ) {
        Column(
            modifier = modifier.padding(all = 16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.onSurface
                )
            )

        }
        Spacer(modifier = modifier.height(14.dp))
        Row (
            modifier= modifier
                .fillMaxWidth()
                .background(Inactivebackground)
                .padding(vertical = 6.dp, horizontal = 16.dp),
        ){
            Text(
                text = caption,
                style = MaterialTheme.typography.caption.copy(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.DarkGray
                )
            )
        }
        Column(
            modifier = modifier.padding(all = 16.dp)
        ) {
            items.forEach {
                item->
                ItemDialogSelect(
                    item = item,
                    selected = selected?.let { it.value == item.value  } ?: false,
                    onClick = {
                        selected = item

                    }
                )
            }
        }
        Row (modifier = modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
            TextButton(onClick = { onDismiss() }) {
                Text(
                    text = stringResource(id = R.string.btn_cancel),
                    style= MaterialTheme.typography.button.copy(
                        color = MaterialTheme.colors.primary.copy(alpha = 0.6f)
                    )
                )
            }
            TextButton(onClick = { selected?.let(onSelected) }) {
                Text(
                    text = stringResource(id = R.string.btn_save),
                    style= MaterialTheme.typography.button.copy(
                        color = MaterialTheme.colors.primary
                    )
                )
            }
        }
    }
}

@Composable
fun ItemDialogSelect(
    modifier: Modifier=Modifier,
    item:DialogItemModel,
    selected:Boolean = false,
    onClick:()->Unit={}
) {
    Row(modifier= modifier
        .fillMaxWidth()
        .background(MaterialTheme.colors.surface),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        RadioButton(
            selected = selected,
            onClick = {
                      onClick()
            },
        colors = RadioButtonDefaults.colors(
            selectedColor = MaterialTheme.colors.primary,
            unselectedColor = MaterialTheme.colors.onBackground,
            disabledColor = Inactivebackground
        ))
        Text(
            text = item.text,
            style= MaterialTheme.typography.body2.copy(
                color = MaterialTheme.colors.onSurface
            )
        )
    }
}
data class DialogItemModel(
    var value:String,
    var text:String
)

@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewDialogSelect(){
    TuduTheme {
        ScreenDialogSelectTheme(
            title = "Select Theme",
            caption = "it will switch to manual sorting mode automatically after dragging tasks to reorder"
        )
    }
}
