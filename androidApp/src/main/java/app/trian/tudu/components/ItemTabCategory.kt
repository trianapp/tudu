package app.trian.tudu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.base.BaseMainApp

@Composable
fun ItemTabCategory(
    modifier: Modifier = Modifier,
    category: String,
    selected: Boolean = false,
    onSelect: (category: String) -> Unit = {},
) {
    Row(
        modifier = modifier
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
                    true -> MaterialTheme.colorScheme.primary
                    false -> Color.LightGray
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
            text = category,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = when (selected) {
                    true -> MaterialTheme.colorScheme.onPrimary
                    else -> Companion.DarkGray
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
fun PreviewTabCategory() {
    BaseMainApp {
        ItemTabCategory(category = "Tugas")
    }
}