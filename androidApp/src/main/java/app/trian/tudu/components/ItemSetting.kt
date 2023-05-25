package app.trian.tudu.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.trian.tudu.base.extensions.getNowMillis
import app.trian.tudu.base.extensions.toReadableDate
import app.trian.tudu.feature.appSetting.AppSettingState

data class ItemSetting(
    var name: String,
    var children: List<SubItemSetting>
)

data class SubItemSetting(
    var name: String,
    var type: String = "link",
    var description: String,
    var route: String,
    var icon: ImageVector
)

@Composable
fun ItemParentSetting(
    modifier: Modifier = Modifier,
    item: ItemSetting,
    appSetting: AppSettingState,
    onNavigate: (route: String) -> Unit = {},
    onClick: (route: String) -> Unit = {}
) {
    Column(
        modifier = Modifier.padding(
            top = 16.dp
        )
    ) {
        Text(
            text = item.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = modifier.height(6.dp))
        item.children.forEach {
            ItemChildSetting(
                itemSetting = it,
                onClick = onClick,
                onNavigate = onNavigate,
                appSetting = appSetting
            )
        }
    }
}

@Composable
fun ItemChildSetting(
    itemSetting: SubItemSetting,
    appSetting: AppSettingState,
    onNavigate: (route: String) -> Unit = {},
    onClick: (route: String) -> Unit = {}
) {
    ListItem(
        modifier = Modifier.clickable {
            if (itemSetting.type == "link" && itemSetting.route.isNotBlank()) {
                onNavigate(itemSetting.route)
            } else {
                onClick(itemSetting.route)
            }
        },
        headlineContent = {
            Text(
                text = itemSetting.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                ),
                color = MaterialTheme.colorScheme.tertiary
            )
        },
        supportingContent = {
            Text(
                text = when (itemSetting.route) {
                    "date_format" -> getNowMillis().toReadableDate(appSetting.dateFormat)
                    "time_format" -> "${if (appSetting.timeFormat == "DEFAULT") "Default System" 
                    else appSetting.timeFormat + " Hours"} "
                    else -> itemSetting.description
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.displaySmall.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                ),
                color = MaterialTheme.colorScheme.inverseSurface
            )
        },
        leadingContent = {
            Icon(
                imageVector = itemSetting.icon,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.outline
            )
        }
    )
}