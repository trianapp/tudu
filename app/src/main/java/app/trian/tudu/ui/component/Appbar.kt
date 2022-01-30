package app.trian.tudu.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.R
import app.trian.tudu.data.local.Category
import app.trian.tudu.ui.component.tab.TabBarHome
import app.trian.tudu.ui.theme.TuduTheme
import compose.icons.Octicons
import compose.icons.octicons.ArrowLeft16
import compose.icons.octicons.Home16
import compose.icons.octicons.Share24

/**
 * App bar home
 * author Trian Damai
 * created_at 29/01/22 - 19.01
 * site https://trian.app
 */
@Composable
fun AppbarHome(
    dataCategory:List<Category> = listOf(),
    onCategoryManagement:()->Unit={},
    onSelectCategory:(category:Category)->Unit={}
) {
    Column {
        TopAppBar(
            title = {
                Text(text = stringResource(R.string.title_appbar_home))
            },
            backgroundColor = MaterialTheme.colorScheme.background,
            navigationIcon = {
                IconToggleButton(checked = false, onCheckedChange = {}) {
                    Icon(imageVector =Octicons.Home16 , contentDescription = "")
                }
            },
            elevation = 0.dp
        )
        TabBarHome(
            tabData = dataCategory,
            onCategoryManagement=onCategoryManagement,
            onSelect = {
                onSelectCategory(it)
            }
        )
    }

}
@Composable
fun AppbarAuth(
    modifier: Modifier=Modifier,
    onBackPressed:()->Unit={}
){
    TopAppBar(
        backgroundColor = MaterialTheme.colorScheme.background,
        elevation = 0.dp,
        navigationIcon = {
            IconToggleButton(checked = false, onCheckedChange = {
                onBackPressed()
            }) {
                androidx.compose.material3.Icon(
                    imageVector = Octicons.ArrowLeft16,
                    contentDescription = ""
                )
            }
        },
        title = {}
    )
}

@Preview
@Composable
fun PreviewAppbarHome(){
    TuduTheme {
           AppbarHome(dataCategory = listOf(
               Category(
                   name = "All",
                   created_at = 0,
                   updated_at = 0
               ),
               Category(
                   name = "Wishlist",
                   created_at = 0,
                   updated_at = 0
               ),
               Category(
                   name = "Tugas",
                   created_at = 0,
                   updated_at = 0
               ),
               Category(
                   name = "Kerjaan",
                   created_at = 0,
                   updated_at = 0
               ),
               Category(
                   name = "Cobaan",
                   created_at = 0,
                   updated_at = 0
               )
           ))
       }

}