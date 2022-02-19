package app.trian.tudu.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.ui.theme.TuduTheme
import compose.icons.Octicons
import compose.icons.octicons.Plus16
import app.trian.tudu.R

/**
 * ItemAddCategory
 * author Trian Damai
 * created_at 29/01/22 - 19.56
 * site https://trian.app
 */

@Composable
fun ItemAddCategory(
    modifier:Modifier=Modifier,
    onAdd:()->Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onAdd() },

    ){
        Row (
            modifier=modifier.padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = Octicons.Plus16,
                contentDescription = stringResource(R.string.content_decription_add_new_category),
                tint = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = modifier.width(6.dp))
            Text(
                text = stringResource(id = R.string.create_new_category),
                style = MaterialTheme.typography.subtitle1.copy(
                    color = MaterialTheme.colors.onBackground
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewItemAddCategory(){
    TuduTheme {
        ItemAddCategory(){

        }
    }
}