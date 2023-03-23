package app.trian.tudu.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.R
import app.trian.tudu.base.BaseMainApp

/**
 * ItemAddCategory
 * author Trian Damai
 * created_at 29/01/22 - 19.56
 * site https://trian.app
 */

@Composable
fun ItemAddTodo(
    modifier: Modifier =Modifier,
    onAdd:()->Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .clickable { onAdd() },

        ){
        Row (
            modifier=modifier.padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = stringResource(id = R.string.content_description_icon_add_todo),
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = modifier.width(6.dp))
            Text(
                text = stringResource(R.string.placeholder_input_todo),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewItemAddTodo(){
    BaseMainApp {
        ItemAddTodo(){

        }
    }
}