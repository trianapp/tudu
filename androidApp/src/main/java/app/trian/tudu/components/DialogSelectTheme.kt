package app.trian.tudu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import app.trian.tudu.R
import app.trian.tudu.data.theme.ThemeData

/**
 * Dialog sorting task
 * author Trian Damai
 * created_at 05/02/22 - 10.37
 * site https://trian.app
 */

@Composable
fun DialogSelectTheme(
    show:Boolean=false,
    title:String="",
    caption:String="",
    items:List<ThemeData> = listOf(),
    selectedItem:ThemeData? = null,
    onDismiss:()->Unit={},
    onSelected:(sort:ThemeData)->Unit={}
) {
    if(show){
        Dialog(
            onDismissRequest =  onDismiss,
            properties = DialogProperties()
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
    items:List<ThemeData> = listOf(),
    selectedItem:ThemeData? = null,
    onDismiss:()->Unit={},
    onSelected:(sort:ThemeData)->Unit={}
) {
    var selected by remember {
        mutableStateOf(selectedItem)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = modifier.padding(all = 16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

        }
        Spacer(modifier = modifier.height(14.dp))
        Row (
            modifier= modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = 6.dp, horizontal = 16.dp),
        ){
            Text(
                text = caption,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 13.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onSurface
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
                    style= MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                )
            }
            TextButton(onClick = { selected?.let(onSelected) }) {
                Text(
                    text = stringResource(id = R.string.btn_save),
                    style= MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun ItemDialogSelect(
    modifier: Modifier=Modifier,
    item:ThemeData,
    selected:Boolean = false,
    onClick:()->Unit={}
) {
    Row(modifier= modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        RadioButton(
            selected = selected,
            onClick = {
                onClick()
            },
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary,
                unselectedColor = MaterialTheme.colorScheme.onBackground
            ))
        Text(
            text = stringResource(id = item.text),
            style= MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}