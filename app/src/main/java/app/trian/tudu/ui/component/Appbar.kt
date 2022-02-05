package app.trian.tudu.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.R
import app.trian.tudu.data.local.Category
import app.trian.tudu.ui.component.tab.TabBarHome
import app.trian.tudu.ui.theme.HexToJetpackColor
import app.trian.tudu.ui.theme.TuduTheme
import compose.icons.Octicons
import compose.icons.octicons.ArrowLeft16
import compose.icons.octicons.ArrowLeft24
import compose.icons.octicons.Home16

/**
 * App bar home
 * author Trian Damai
 * created_at 29/01/22 - 19.01
 * site https://trian.app
 */
@Composable
fun AppbarHome(
    dataCategory:List<Category> = listOf(),
    onOptionMenuSelected:(menu:Int)->Unit={},
    onSelectCategory:(category:Category)->Unit={}
) {
    Column {
        TopAppBar(
            title = {
                Text(text = stringResource(R.string.title_appbar_home))
            },
            backgroundColor = MaterialTheme.colors.background,
            navigationIcon = {
                IconToggleButton(checked = false, onCheckedChange = {}) {
                    Icon(imageVector =Octicons.Home16 , contentDescription = "")
                }
            },
            elevation = 0.dp
        )
        TabBarHome(
            tabData = dataCategory,
            onOptionMenuClicked=onOptionMenuSelected,
            onSelect =onSelectCategory
        )
    }

}
@Composable
fun AppbarAuth(
    modifier: Modifier=Modifier,
    onBackPressed:()->Unit={}
){
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        navigationIcon = {
            IconToggleButton(checked = false, onCheckedChange = {
                onBackPressed()
            }) {
                Icon(
                    imageVector = Octicons.ArrowLeft16,
                    contentDescription = ""
                )
            }
        },
        title = {}
    )
}
@Composable
fun AppbarBasic(
    title:String,
    onBackPressed: () -> Unit={}
){
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        navigationIcon = {
            IconToggleButton(checked = false, onCheckedChange = {onBackPressed()}) {
                Icon(
                    imageVector = Octicons.ArrowLeft24,
                    contentDescription = ""
                )
            }
        },
        title = {
            Text(text = title)
        }
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
                   updated_at = 0,
                   color = HexToJetpackColor.Blue,
               ),
               Category(
                   name = "Wishlist",
                   created_at = 0,
                   updated_at = 0,
                   color = HexToJetpackColor.Blue,
               ),
               Category(
                   name = "Tugas",
                   created_at = 0,
                   updated_at = 0,
                   color = HexToJetpackColor.Blue,
               ),
               Category(
                   name = "Kerjaan",
                   created_at = 0,
                   updated_at = 0,
                   color = HexToJetpackColor.Blue,
               ),
               Category(
                   name = "Cobaan",
                   created_at = 0,
                   updated_at = 0,
                   color = HexToJetpackColor.Blue,
               )
           ))
       }

}

@Preview
@Composable
fun PreviewAppbarBasic(){
    TuduTheme {
        AppbarBasic(title = "Change password")
    }
}