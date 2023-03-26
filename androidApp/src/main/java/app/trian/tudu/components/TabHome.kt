package app.trian.tudu.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.base.BaseMainApp
import app.trian.tudu.data.model.CategoryModel


/**
 * Tab bar
 * author Trian Damai
 * created_at 30/01/22 - 12.29
 * site https://trian.app
 */
@Composable
fun TabBarHome(
    modifier: Modifier = Modifier,
    tabData: List<CategoryModel> = listOf(),
    onSelect: (category: CategoryModel) -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LazyRow(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            content = {
                items(count = tabData.size) { index: Int ->
                    Spacer(modifier = Modifier.width(if (index == 0) 16.dp else 8.dp))
                    FilterChip(
                        onClick = {
                            selectedTab = index
                            onSelect(tabData[index])
                        },
                        enabled = true,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            labelColor = MaterialTheme.colorScheme.surfaceVariant,
                            iconColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        label = {
                            Text(
                                text = tabData[index].categoryName,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        selected = selectedTab == index
                    )
                }
            })

    }
}

@Preview
@Composable
fun PreviewTabHome() {
    BaseMainApp {
        TabBarHome(
            tabData = listOf(
                CategoryModel(
                    categoryName = "All"
                ),
                CategoryModel(
                    categoryName = "Kerja"
                )
            )
        )
    }
}