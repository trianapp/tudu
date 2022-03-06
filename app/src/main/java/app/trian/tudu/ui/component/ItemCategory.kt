package app.trian.tudu.ui.component

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.R
import app.trian.tudu.data.local.Category
import app.trian.tudu.ui.component.dialog.DropdownActionItemCategory
import app.trian.tudu.ui.theme.HexToJetpackColor
import app.trian.tudu.ui.theme.TuduTheme
import compose.icons.Octicons
import compose.icons.octicons.Dot16
import compose.icons.octicons.Quote16
import java.time.OffsetDateTime

/**
 * Item Category
 * author Trian Damai
 * created_at 29/01/22 - 19.41
 * site https://trian.app
 */

@Composable
fun ItemCategory(
    modifier:Modifier=Modifier,
    category: Category,
    onEdit:(category:Category)->Unit={},
    onDelete:(category:Category)->Unit={},
    onHide:(category:Category)->Unit={}
) {
    var showDropdownAction by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 4.dp)
    ){
        Row(
            modifier=modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row (
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
                    ){
                Box(
                    modifier = modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(HexToJetpackColor.getColor(category.color))
                )
                Spacer(modifier = modifier.width(16.dp))
                Text(
                    text = category.name,
                    style=MaterialTheme.typography.body2.copy(
                        MaterialTheme.colors.onBackground
                    )
                )
            }
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${category.usedCount}",
                    style = MaterialTheme.typography.body2.copy(
                        color = MaterialTheme.colors.onBackground
                    )
                )
                Spacer(modifier = modifier.width(16.dp))
                IconToggleButton(
                    checked = showDropdownAction,
                    onCheckedChange ={
                    showDropdownAction=true
                }) {
                    DropdownActionItemCategory(
                        show = showDropdownAction,
                        onHide = {onHide(category)},
                        onDelete = {onDelete(category)},
                        onEdit = {onEdit(category)},
                        onDismiss = {showDropdownAction=false}
                    )
                    Icon(
                        imageVector = Octicons.Quote16,
                        contentDescription = stringResource(R.string.content_description_option_menu_category),
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            }
        }
    }
}

@SuppressLint("NewApi")
@Preview(
    uiMode=UI_MODE_NIGHT_NO
)
@Preview(
    uiMode=UI_MODE_NIGHT_YES
)
@Composable
fun PreviewItemCategory(){
    TuduTheme {
        ItemCategory(
            category = Category(
                categoryId = "ini id",
                name = "Wishlist",
                color = HexToJetpackColor.Blue,
                created_at = OffsetDateTime.now(),
                updated_at = OffsetDateTime.now()
            )
        )
    }
}