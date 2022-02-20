package app.trian.tudu.ui.component.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.data.local.Category
import app.trian.tudu.ui.theme.InactiveText
import app.trian.tudu.ui.theme.Inactivebackground
import app.trian.tudu.ui.theme.TuduTheme

@Composable
fun ItemTabCategory(
    modifier:Modifier=Modifier,
    category: Category,
    selected:Boolean=false,
    onSelect:(category: Category)->Unit={},
) {
    Row(
        modifier= modifier
            .clip(
                RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            )
            .background(
                color = when (selected) {
                    true -> MaterialTheme.colors.primary
                    false -> Inactivebackground
                }
            )
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            )
            .clickable {
                onSelect(category)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = modifier.width(6.dp))
        Text(
            text = category.name,
            style= MaterialTheme.typography.body2.copy(
                color = when (selected) {
                    true -> MaterialTheme.colors.onPrimary
                    else -> InactiveText
                },
                fontWeight = when (selected) {
                    true -> FontWeight.Bold
                    else -> FontWeight.Normal
                }
            )
        )
        Spacer(modifier = modifier.width(6.dp))
    }
}

@Preview
@Composable
fun PreviewTabCategory(){
    TuduTheme {
        ItemTabCategory(category = Category(
            name = "Tugas"
        ))
    }
}