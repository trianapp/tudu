package app.trian.tudu.ui.component.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.R
import app.trian.tudu.common.customTabIndicatorOffset
import app.trian.tudu.data.local.Category
import app.trian.tudu.ui.component.category.ItemTabCategory
import app.trian.tudu.ui.component.dialog.DropdownActionTabCategory
import app.trian.tudu.ui.theme.HexToJetpackColor
import app.trian.tudu.ui.theme.InactiveText
import app.trian.tudu.ui.theme.Inactivebackground
import app.trian.tudu.ui.theme.TuduTheme
import compose.icons.Octicons
import compose.icons.octicons.Grabber16
import compose.icons.octicons.Home16

/**
 * Tab bar
 * author Trian Damai
 * created_at 30/01/22 - 12.29
 * site https://trian.app
 */
@Composable
fun TabBarHome(
    modifier: Modifier=Modifier,
    tabData:List<Category> = listOf(),
    onOptionMenuClicked:(menu:Int)->Unit={},
    onSelect:(category:Category)->Unit={},
) {

    var selectedTab by remember {
        mutableStateOf(0)
    }
    var showDropDownOptionCategory by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LazyRow(
            modifier=modifier.fillMaxWidth(fraction = 0.9f),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            content = {
                item {

                    Spacer(modifier = modifier.width(30.dp))

                    ItemTabCategory(
                        category = Category(
                            name = stringResource(R.string.category_all),
                            created_at = 0,
                            updated_at = 0,
                            color = HexToJetpackColor.Blue
                        ),
                        selected = selectedTab == 0,
                        onSelect = {
                            onSelect(it)
                            selectedTab =0
                        }
                    )
                    Spacer(modifier = modifier.width(10.dp))
                }
            items(count = tabData.size){
                index: Int ->
                Spacer(modifier = modifier.width(10.dp))
                ItemTabCategory(
                    category = tabData[index],
                    selected = index == selectedTab-1,
                    onSelect = {
                        onSelect(it)
                        selectedTab = index +1
                    }
                )
                Spacer(modifier = modifier.width(10.dp))
            }
        })

        IconToggleButton(
            checked = false,
            onCheckedChange = {
                showDropDownOptionCategory=true
            }
        ) {
            Icon(imageVector = Octicons.Grabber16, contentDescription = "")
            DropdownActionTabCategory(
                show =showDropDownOptionCategory ,
                onDismiss = {
                            showDropDownOptionCategory = false
                },
                onSelected = {
                    onOptionMenuClicked(it)
                    showDropDownOptionCategory=false
                }
            )
        }

    }
}

@Preview
@Composable
fun PreviewTabHome() {
    TuduTheme {
        TabBarHome(
            tabData = listOf(
                Category(
                    name = "Wishlist",
                    created_at = 0,
                    updated_at = 0,
                    color = HexToJetpackColor.Blue
                ),
                Category(
                    name = "Tugas",
                    created_at = 0,
                    updated_at = 0,
                    color = HexToJetpackColor.Blue
                ),
                Category(
                    name = "Kerjaan",
                    created_at = 0,
                    updated_at = 0,
                    color = HexToJetpackColor.Blue
                ),
                Category(
                    name = "Cobaan",
                    created_at = 0,
                    updated_at = 0,
                    color = HexToJetpackColor.Blue
                )
            ),

        )
    }
}